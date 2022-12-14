package com.eg.godns.dns.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.CharsetUtil;

public class DnsCodecUtil {
    static public void encodeDomainName(String name, ByteBuf buf) {
        if (".".equals(name)) {
            buf.writeByte(0);
        } else {
            String[] labels = name.split("\\.");
            String[] var3 = labels;
            int var4 = labels.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String label = var3[var5];
                int labelLen = label.length();
                if (labelLen == 0) {
                    break;
                }

                buf.writeByte(labelLen);
                ByteBufUtil.writeAscii(buf, label);
            }

            buf.writeByte(0);
        }
    }

    static public String decodeDomainName(ByteBuf in) {
        int position = -1;
        int checked = 0;
        int end = in.writerIndex();
        int readable = in.readableBytes();
        if (readable == 0) {
            return ".";
        } else {
            StringBuilder name = new StringBuilder(readable << 1);

            while(in.isReadable()) {
                int len = in.readUnsignedByte();
                boolean pointer = (len & 192) == 192;
                if (pointer) {
                    if (position == -1) {
                        position = in.readerIndex() + 1;
                    }

                    if (!in.isReadable()) {
                        throw new CorruptedFrameException("truncated pointer in a name");
                    }

                    int next = (len & 63) << 8 | in.readUnsignedByte();
                    if (next >= end) {
                        throw new CorruptedFrameException("name has an out-of-range pointer");
                    }

                    in.readerIndex(next);
                    checked += 2;
                    if (checked >= end) {
                        throw new CorruptedFrameException("name contains a loop.");
                    }
                } else {
                    if (len == 0) {
                        break;
                    }

                    if (!in.isReadable(len)) {
                        throw new CorruptedFrameException("truncated label in a name");
                    }

                    name.append(in.toString(in.readerIndex(), len, CharsetUtil.UTF_8)).append('.');
                    in.skipBytes(len);
                }
            }

            if (position != -1) {
                in.readerIndex(position);
            }

            if (name.length() == 0) {
                return ".";
            } else {
                if (name.charAt(name.length() - 1) != '.') {
                    name.append('.');
                }

                return name.toString();
            }
        }
    }

    static ByteBuf decompressDomainName(ByteBuf compression) {
        String domainName = decodeDomainName(compression);
        ByteBuf result = compression.alloc().buffer(domainName.length() << 1);
        encodeDomainName(domainName, result);
        return result;
    }
}
