package ru.bmstu.iu3.grpc.menu;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Справочник меню (сервис B — Reference Service).
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.1)",
    comments = "Source: menu_catalog.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MenuCatalogGrpc {

  private MenuCatalogGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ru.bmstu.iu3.grpc.menu.MenuCatalog";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.GetMenuRequest,
      ru.bmstu.iu3.grpc.menu.GetMenuResponse> getGetMenuMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMenu",
      requestType = ru.bmstu.iu3.grpc.menu.GetMenuRequest.class,
      responseType = ru.bmstu.iu3.grpc.menu.GetMenuResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.GetMenuRequest,
      ru.bmstu.iu3.grpc.menu.GetMenuResponse> getGetMenuMethod() {
    io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.GetMenuRequest, ru.bmstu.iu3.grpc.menu.GetMenuResponse> getGetMenuMethod;
    if ((getGetMenuMethod = MenuCatalogGrpc.getGetMenuMethod) == null) {
      synchronized (MenuCatalogGrpc.class) {
        if ((getGetMenuMethod = MenuCatalogGrpc.getGetMenuMethod) == null) {
          MenuCatalogGrpc.getGetMenuMethod = getGetMenuMethod =
              io.grpc.MethodDescriptor.<ru.bmstu.iu3.grpc.menu.GetMenuRequest, ru.bmstu.iu3.grpc.menu.GetMenuResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMenu"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.bmstu.iu3.grpc.menu.GetMenuRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.bmstu.iu3.grpc.menu.GetMenuResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MenuCatalogMethodDescriptorSupplier("GetMenu"))
              .build();
        }
      }
    }
    return getGetMenuMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.ValidateDishRequest,
      ru.bmstu.iu3.grpc.menu.ValidateDishResponse> getValidateDishNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateDishNumber",
      requestType = ru.bmstu.iu3.grpc.menu.ValidateDishRequest.class,
      responseType = ru.bmstu.iu3.grpc.menu.ValidateDishResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.ValidateDishRequest,
      ru.bmstu.iu3.grpc.menu.ValidateDishResponse> getValidateDishNumberMethod() {
    io.grpc.MethodDescriptor<ru.bmstu.iu3.grpc.menu.ValidateDishRequest, ru.bmstu.iu3.grpc.menu.ValidateDishResponse> getValidateDishNumberMethod;
    if ((getValidateDishNumberMethod = MenuCatalogGrpc.getValidateDishNumberMethod) == null) {
      synchronized (MenuCatalogGrpc.class) {
        if ((getValidateDishNumberMethod = MenuCatalogGrpc.getValidateDishNumberMethod) == null) {
          MenuCatalogGrpc.getValidateDishNumberMethod = getValidateDishNumberMethod =
              io.grpc.MethodDescriptor.<ru.bmstu.iu3.grpc.menu.ValidateDishRequest, ru.bmstu.iu3.grpc.menu.ValidateDishResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidateDishNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.bmstu.iu3.grpc.menu.ValidateDishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.bmstu.iu3.grpc.menu.ValidateDishResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MenuCatalogMethodDescriptorSupplier("ValidateDishNumber"))
              .build();
        }
      }
    }
    return getValidateDishNumberMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MenuCatalogStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MenuCatalogStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MenuCatalogStub>() {
        @java.lang.Override
        public MenuCatalogStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MenuCatalogStub(channel, callOptions);
        }
      };
    return MenuCatalogStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MenuCatalogBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MenuCatalogBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MenuCatalogBlockingStub>() {
        @java.lang.Override
        public MenuCatalogBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MenuCatalogBlockingStub(channel, callOptions);
        }
      };
    return MenuCatalogBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MenuCatalogFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MenuCatalogFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MenuCatalogFutureStub>() {
        @java.lang.Override
        public MenuCatalogFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MenuCatalogFutureStub(channel, callOptions);
        }
      };
    return MenuCatalogFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Справочник меню (сервис B — Reference Service).
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void getMenu(ru.bmstu.iu3.grpc.menu.GetMenuRequest request,
        io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.GetMenuResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMenuMethod(), responseObserver);
    }

    /**
     */
    default void validateDishNumber(ru.bmstu.iu3.grpc.menu.ValidateDishRequest request,
        io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.ValidateDishResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateDishNumberMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MenuCatalog.
   * <pre>
   * Справочник меню (сервис B — Reference Service).
   * </pre>
   */
  public static abstract class MenuCatalogImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MenuCatalogGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MenuCatalog.
   * <pre>
   * Справочник меню (сервис B — Reference Service).
   * </pre>
   */
  public static final class MenuCatalogStub
      extends io.grpc.stub.AbstractAsyncStub<MenuCatalogStub> {
    private MenuCatalogStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MenuCatalogStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MenuCatalogStub(channel, callOptions);
    }

    /**
     */
    public void getMenu(ru.bmstu.iu3.grpc.menu.GetMenuRequest request,
        io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.GetMenuResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMenuMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validateDishNumber(ru.bmstu.iu3.grpc.menu.ValidateDishRequest request,
        io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.ValidateDishResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateDishNumberMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MenuCatalog.
   * <pre>
   * Справочник меню (сервис B — Reference Service).
   * </pre>
   */
  public static final class MenuCatalogBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MenuCatalogBlockingStub> {
    private MenuCatalogBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MenuCatalogBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MenuCatalogBlockingStub(channel, callOptions);
    }

    /**
     */
    public ru.bmstu.iu3.grpc.menu.GetMenuResponse getMenu(ru.bmstu.iu3.grpc.menu.GetMenuRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMenuMethod(), getCallOptions(), request);
    }

    /**
     */
    public ru.bmstu.iu3.grpc.menu.ValidateDishResponse validateDishNumber(ru.bmstu.iu3.grpc.menu.ValidateDishRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateDishNumberMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MenuCatalog.
   * <pre>
   * Справочник меню (сервис B — Reference Service).
   * </pre>
   */
  public static final class MenuCatalogFutureStub
      extends io.grpc.stub.AbstractFutureStub<MenuCatalogFutureStub> {
    private MenuCatalogFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MenuCatalogFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MenuCatalogFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ru.bmstu.iu3.grpc.menu.GetMenuResponse> getMenu(
        ru.bmstu.iu3.grpc.menu.GetMenuRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMenuMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ru.bmstu.iu3.grpc.menu.ValidateDishResponse> validateDishNumber(
        ru.bmstu.iu3.grpc.menu.ValidateDishRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateDishNumberMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_MENU = 0;
  private static final int METHODID_VALIDATE_DISH_NUMBER = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MENU:
          serviceImpl.getMenu((ru.bmstu.iu3.grpc.menu.GetMenuRequest) request,
              (io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.GetMenuResponse>) responseObserver);
          break;
        case METHODID_VALIDATE_DISH_NUMBER:
          serviceImpl.validateDishNumber((ru.bmstu.iu3.grpc.menu.ValidateDishRequest) request,
              (io.grpc.stub.StreamObserver<ru.bmstu.iu3.grpc.menu.ValidateDishResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetMenuMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ru.bmstu.iu3.grpc.menu.GetMenuRequest,
              ru.bmstu.iu3.grpc.menu.GetMenuResponse>(
                service, METHODID_GET_MENU)))
        .addMethod(
          getValidateDishNumberMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ru.bmstu.iu3.grpc.menu.ValidateDishRequest,
              ru.bmstu.iu3.grpc.menu.ValidateDishResponse>(
                service, METHODID_VALIDATE_DISH_NUMBER)))
        .build();
  }

  private static abstract class MenuCatalogBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MenuCatalogBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ru.bmstu.iu3.grpc.menu.MenuCatalogProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MenuCatalog");
    }
  }

  private static final class MenuCatalogFileDescriptorSupplier
      extends MenuCatalogBaseDescriptorSupplier {
    MenuCatalogFileDescriptorSupplier() {}
  }

  private static final class MenuCatalogMethodDescriptorSupplier
      extends MenuCatalogBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MenuCatalogMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (MenuCatalogGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MenuCatalogFileDescriptorSupplier())
              .addMethod(getGetMenuMethod())
              .addMethod(getValidateDishNumberMethod())
              .build();
        }
      }
    }
    return result;
  }
}
