package com.eg.gofacade.dto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.48.0)",
    comments = "Source: GoAway.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GoServiceGrpc {

  private GoServiceGrpc() {}

  public static final String SERVICE_NAME = "com.eg.gofacade.dto.GoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.eg.gofacade.dto.HelloRequest,
      com.eg.gofacade.dto.GoResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = com.eg.gofacade.dto.HelloRequest.class,
      responseType = com.eg.gofacade.dto.GoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.eg.gofacade.dto.HelloRequest,
      com.eg.gofacade.dto.GoResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<com.eg.gofacade.dto.HelloRequest, com.eg.gofacade.dto.GoResponse> getHelloMethod;
    if ((getHelloMethod = GoServiceGrpc.getHelloMethod) == null) {
      synchronized (GoServiceGrpc.class) {
        if ((getHelloMethod = GoServiceGrpc.getHelloMethod) == null) {
          GoServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<com.eg.gofacade.dto.HelloRequest, com.eg.gofacade.dto.GoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.GoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GoServiceMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.eg.gofacade.dto.AskRequest,
      com.eg.gofacade.dto.GoResponse> getAskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ask",
      requestType = com.eg.gofacade.dto.AskRequest.class,
      responseType = com.eg.gofacade.dto.GoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.eg.gofacade.dto.AskRequest,
      com.eg.gofacade.dto.GoResponse> getAskMethod() {
    io.grpc.MethodDescriptor<com.eg.gofacade.dto.AskRequest, com.eg.gofacade.dto.GoResponse> getAskMethod;
    if ((getAskMethod = GoServiceGrpc.getAskMethod) == null) {
      synchronized (GoServiceGrpc.class) {
        if ((getAskMethod = GoServiceGrpc.getAskMethod) == null) {
          GoServiceGrpc.getAskMethod = getAskMethod =
              io.grpc.MethodDescriptor.<com.eg.gofacade.dto.AskRequest, com.eg.gofacade.dto.GoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.AskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.GoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GoServiceMethodDescriptorSupplier("ask"))
              .build();
        }
      }
    }
    return getAskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.eg.gofacade.dto.TalkRequest,
      com.eg.gofacade.dto.GoResponse> getTalkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "talk",
      requestType = com.eg.gofacade.dto.TalkRequest.class,
      responseType = com.eg.gofacade.dto.GoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.eg.gofacade.dto.TalkRequest,
      com.eg.gofacade.dto.GoResponse> getTalkMethod() {
    io.grpc.MethodDescriptor<com.eg.gofacade.dto.TalkRequest, com.eg.gofacade.dto.GoResponse> getTalkMethod;
    if ((getTalkMethod = GoServiceGrpc.getTalkMethod) == null) {
      synchronized (GoServiceGrpc.class) {
        if ((getTalkMethod = GoServiceGrpc.getTalkMethod) == null) {
          GoServiceGrpc.getTalkMethod = getTalkMethod =
              io.grpc.MethodDescriptor.<com.eg.gofacade.dto.TalkRequest, com.eg.gofacade.dto.GoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "talk"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.TalkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.GoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GoServiceMethodDescriptorSupplier("talk"))
              .build();
        }
      }
    }
    return getTalkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.eg.gofacade.dto.ByeRequest,
      com.eg.gofacade.dto.GoResponse> getByeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bye",
      requestType = com.eg.gofacade.dto.ByeRequest.class,
      responseType = com.eg.gofacade.dto.GoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.eg.gofacade.dto.ByeRequest,
      com.eg.gofacade.dto.GoResponse> getByeMethod() {
    io.grpc.MethodDescriptor<com.eg.gofacade.dto.ByeRequest, com.eg.gofacade.dto.GoResponse> getByeMethod;
    if ((getByeMethod = GoServiceGrpc.getByeMethod) == null) {
      synchronized (GoServiceGrpc.class) {
        if ((getByeMethod = GoServiceGrpc.getByeMethod) == null) {
          GoServiceGrpc.getByeMethod = getByeMethod =
              io.grpc.MethodDescriptor.<com.eg.gofacade.dto.ByeRequest, com.eg.gofacade.dto.GoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "bye"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.ByeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.eg.gofacade.dto.GoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GoServiceMethodDescriptorSupplier("bye"))
              .build();
        }
      }
    }
    return getByeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GoServiceStub>() {
        @java.lang.Override
        public GoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GoServiceStub(channel, callOptions);
        }
      };
    return GoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GoServiceBlockingStub>() {
        @java.lang.Override
        public GoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GoServiceBlockingStub(channel, callOptions);
        }
      };
    return GoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GoServiceFutureStub>() {
        @java.lang.Override
        public GoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GoServiceFutureStub(channel, callOptions);
        }
      };
    return GoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void hello(com.eg.gofacade.dto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    /**
     */
    public void ask(com.eg.gofacade.dto.AskRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAskMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.eg.gofacade.dto.TalkRequest> talk(
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getTalkMethod(), responseObserver);
    }

    /**
     */
    public void bye(com.eg.gofacade.dto.ByeRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getByeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.eg.gofacade.dto.HelloRequest,
                com.eg.gofacade.dto.GoResponse>(
                  this, METHODID_HELLO)))
          .addMethod(
            getAskMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                com.eg.gofacade.dto.AskRequest,
                com.eg.gofacade.dto.GoResponse>(
                  this, METHODID_ASK)))
          .addMethod(
            getTalkMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                com.eg.gofacade.dto.TalkRequest,
                com.eg.gofacade.dto.GoResponse>(
                  this, METHODID_TALK)))
          .addMethod(
            getByeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.eg.gofacade.dto.ByeRequest,
                com.eg.gofacade.dto.GoResponse>(
                  this, METHODID_BYE)))
          .build();
    }
  }

  /**
   */
  public static final class GoServiceStub extends io.grpc.stub.AbstractAsyncStub<GoServiceStub> {
    private GoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GoServiceStub(channel, callOptions);
    }

    /**
     */
    public void hello(com.eg.gofacade.dto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ask(com.eg.gofacade.dto.AskRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getAskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.eg.gofacade.dto.TalkRequest> talk(
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getTalkMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void bye(com.eg.gofacade.dto.ByeRequest request,
        io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getByeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<GoServiceBlockingStub> {
    private GoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.eg.gofacade.dto.GoResponse hello(com.eg.gofacade.dto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.eg.gofacade.dto.GoResponse> ask(
        com.eg.gofacade.dto.AskRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getAskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.eg.gofacade.dto.GoResponse bye(com.eg.gofacade.dto.ByeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getByeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<GoServiceFutureStub> {
    private GoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.eg.gofacade.dto.GoResponse> hello(
        com.eg.gofacade.dto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.eg.gofacade.dto.GoResponse> bye(
        com.eg.gofacade.dto.ByeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getByeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;
  private static final int METHODID_ASK = 1;
  private static final int METHODID_BYE = 2;
  private static final int METHODID_TALK = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((com.eg.gofacade.dto.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse>) responseObserver);
          break;
        case METHODID_ASK:
          serviceImpl.ask((com.eg.gofacade.dto.AskRequest) request,
              (io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse>) responseObserver);
          break;
        case METHODID_BYE:
          serviceImpl.bye((com.eg.gofacade.dto.ByeRequest) request,
              (io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TALK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.talk(
              (io.grpc.stub.StreamObserver<com.eg.gofacade.dto.GoResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.eg.gofacade.dto.GoAway.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GoService");
    }
  }

  private static final class GoServiceFileDescriptorSupplier
      extends GoServiceBaseDescriptorSupplier {
    GoServiceFileDescriptorSupplier() {}
  }

  private static final class GoServiceMethodDescriptorSupplier
      extends GoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GoServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GoServiceFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .addMethod(getAskMethod())
              .addMethod(getTalkMethod())
              .addMethod(getByeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
