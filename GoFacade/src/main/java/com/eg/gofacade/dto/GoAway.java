// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GoAway.proto

package com.eg.gofacade.dto;

public final class GoAway {
  private GoAway() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_eg_gofacade_dto_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_eg_gofacade_dto_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_eg_gofacade_dto_AskRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_eg_gofacade_dto_AskRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_eg_gofacade_dto_TalkRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_eg_gofacade_dto_TalkRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_eg_gofacade_dto_ByeRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_eg_gofacade_dto_ByeRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_eg_gofacade_dto_GoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_eg_gofacade_dto_GoResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014GoAway.proto\022\023com.eg.gofacade.dto\")\n\014H" +
      "elloRequest\022\013\n\003cid\030\001 \001(\t\022\014\n\004word\030\002 \001(\t\"5" +
      "\n\nAskRequest\022\013\n\003cid\030\001 \001(\t\022\014\n\004host\030\002 \001(\t\022" +
      "\014\n\004port\030\003 \001(\005\"5\n\013TalkRequest\022\013\n\003cid\030\001 \001(" +
      "\t\022\013\n\003seq\030\002 \001(\005\022\014\n\004data\030\003 \001(\014\"\031\n\nByeReque" +
      "st\022\013\n\003cid\030\001 \001(\t\"7\n\nGoResponse\022\013\n\003cid\030\001 \001" +
      "(\t\022\016\n\006status\030\002 \001(\005\022\014\n\004data\030\003 \001(\014*\200\001\n\010GoS" +
      "tatus\022\014\n\010ST_START\020\000\022\n\n\005ST_OK\020\310\001\022\013\n\006ST_BA" +
      "D\020\220\003\022\014\n\007ST_GONE\020\232\003\022\021\n\014ST_INNER_ERR\020\364\003\022\r\n" +
      "\010ST_BAD_G\020\366\003\022\016\n\tST_SRV_UA\020\367\003\022\r\n\010ST_GW_TO" +
      "\020\370\0032\273\002\n\tGoService\022K\n\005hello\022!.com.eg.gofa" +
      "cade.dto.HelloRequest\032\037.com.eg.gofacade." +
      "dto.GoResponse\022I\n\003ask\022\037.com.eg.gofacade." +
      "dto.AskRequest\032\037.com.eg.gofacade.dto.GoR" +
      "esponse0\001\022M\n\004talk\022 .com.eg.gofacade.dto." +
      "TalkRequest\032\037.com.eg.gofacade.dto.GoResp" +
      "onse(\0010\001\022G\n\003bye\022\037.com.eg.gofacade.dto.By" +
      "eRequest\032\037.com.eg.gofacade.dto.GoRespons" +
      "eB\027\n\023com.eg.gofacade.dtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_eg_gofacade_dto_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_eg_gofacade_dto_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_eg_gofacade_dto_HelloRequest_descriptor,
        new java.lang.String[] { "Cid", "Word", });
    internal_static_com_eg_gofacade_dto_AskRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_eg_gofacade_dto_AskRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_eg_gofacade_dto_AskRequest_descriptor,
        new java.lang.String[] { "Cid", "Host", "Port", });
    internal_static_com_eg_gofacade_dto_TalkRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_eg_gofacade_dto_TalkRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_eg_gofacade_dto_TalkRequest_descriptor,
        new java.lang.String[] { "Cid", "Seq", "Data", });
    internal_static_com_eg_gofacade_dto_ByeRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_eg_gofacade_dto_ByeRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_eg_gofacade_dto_ByeRequest_descriptor,
        new java.lang.String[] { "Cid", });
    internal_static_com_eg_gofacade_dto_GoResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_eg_gofacade_dto_GoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_eg_gofacade_dto_GoResponse_descriptor,
        new java.lang.String[] { "Cid", "Status", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
