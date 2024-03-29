// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: lifestream.proto

public final class Lifestream {
  private Lifestream() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ImageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required bytes imagedata = 1;
    /**
     * <code>required bytes imagedata = 1;</code>
     */
    boolean hasImagedata();
    /**
     * <code>required bytes imagedata = 1;</code>
     */
    com.google.protobuf.ByteString getImagedata();
  }
  /**
   * Protobuf type {@code Image}
   */
  public static final class Image extends
      com.google.protobuf.GeneratedMessage
      implements ImageOrBuilder {
    // Use Image.newBuilder() to construct.
    private Image(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Image(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Image defaultInstance;
    public static Image getDefaultInstance() {
      return defaultInstance;
    }

    public Image getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Image(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              imagedata_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Lifestream.internal_static_Image_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Lifestream.internal_static_Image_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Lifestream.Image.class, Lifestream.Image.Builder.class);
    }

    public static com.google.protobuf.Parser<Image> PARSER =
        new com.google.protobuf.AbstractParser<Image>() {
      public Image parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Image(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Image> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required bytes imagedata = 1;
    public static final int IMAGEDATA_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString imagedata_;
    /**
     * <code>required bytes imagedata = 1;</code>
     */
    public boolean hasImagedata() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bytes imagedata = 1;</code>
     */
    public com.google.protobuf.ByteString getImagedata() {
      return imagedata_;
    }

    private void initFields() {
      imagedata_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasImagedata()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, imagedata_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, imagedata_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static Lifestream.Image parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Lifestream.Image parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Lifestream.Image parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Lifestream.Image parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Lifestream.Image parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Lifestream.Image parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static Lifestream.Image parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static Lifestream.Image parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static Lifestream.Image parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Lifestream.Image parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(Lifestream.Image prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Image}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements Lifestream.ImageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Lifestream.internal_static_Image_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Lifestream.internal_static_Image_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Lifestream.Image.class, Lifestream.Image.Builder.class);
      }

      // Construct using Lifestream.Image.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        imagedata_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Lifestream.internal_static_Image_descriptor;
      }

      public Lifestream.Image getDefaultInstanceForType() {
        return Lifestream.Image.getDefaultInstance();
      }

      public Lifestream.Image build() {
        Lifestream.Image result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Lifestream.Image buildPartial() {
        Lifestream.Image result = new Lifestream.Image(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.imagedata_ = imagedata_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Lifestream.Image) {
          return mergeFrom((Lifestream.Image)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Lifestream.Image other) {
        if (other == Lifestream.Image.getDefaultInstance()) return this;
        if (other.hasImagedata()) {
          setImagedata(other.getImagedata());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasImagedata()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Lifestream.Image parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Lifestream.Image) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required bytes imagedata = 1;
      private com.google.protobuf.ByteString imagedata_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes imagedata = 1;</code>
       */
      public boolean hasImagedata() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bytes imagedata = 1;</code>
       */
      public com.google.protobuf.ByteString getImagedata() {
        return imagedata_;
      }
      /**
       * <code>required bytes imagedata = 1;</code>
       */
      public Builder setImagedata(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        imagedata_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes imagedata = 1;</code>
       */
      public Builder clearImagedata() {
        bitField0_ = (bitField0_ & ~0x00000001);
        imagedata_ = getDefaultInstance().getImagedata();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Image)
    }

    static {
      defaultInstance = new Image(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Image)
  }

  public interface LifeStreamOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // repeated .Image image = 1;
    /**
     * <code>repeated .Image image = 1;</code>
     */
    java.util.List<Lifestream.Image> 
        getImageList();
    /**
     * <code>repeated .Image image = 1;</code>
     */
    Lifestream.Image getImage(int index);
    /**
     * <code>repeated .Image image = 1;</code>
     */
    int getImageCount();
    /**
     * <code>repeated .Image image = 1;</code>
     */
    java.util.List<? extends Lifestream.ImageOrBuilder> 
        getImageOrBuilderList();
    /**
     * <code>repeated .Image image = 1;</code>
     */
    Lifestream.ImageOrBuilder getImageOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code LifeStream}
   */
  public static final class LifeStream extends
      com.google.protobuf.GeneratedMessage
      implements LifeStreamOrBuilder {
    // Use LifeStream.newBuilder() to construct.
    private LifeStream(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private LifeStream(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final LifeStream defaultInstance;
    public static LifeStream getDefaultInstance() {
      return defaultInstance;
    }

    public LifeStream getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private LifeStream(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                image_ = new java.util.ArrayList<Lifestream.Image>();
                mutable_bitField0_ |= 0x00000001;
              }
              image_.add(input.readMessage(Lifestream.Image.PARSER, extensionRegistry));
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          image_ = java.util.Collections.unmodifiableList(image_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Lifestream.internal_static_LifeStream_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Lifestream.internal_static_LifeStream_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Lifestream.LifeStream.class, Lifestream.LifeStream.Builder.class);
    }

    public static com.google.protobuf.Parser<LifeStream> PARSER =
        new com.google.protobuf.AbstractParser<LifeStream>() {
      public LifeStream parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new LifeStream(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<LifeStream> getParserForType() {
      return PARSER;
    }

    // repeated .Image image = 1;
    public static final int IMAGE_FIELD_NUMBER = 1;
    private java.util.List<Lifestream.Image> image_;
    /**
     * <code>repeated .Image image = 1;</code>
     */
    public java.util.List<Lifestream.Image> getImageList() {
      return image_;
    }
    /**
     * <code>repeated .Image image = 1;</code>
     */
    public java.util.List<? extends Lifestream.ImageOrBuilder> 
        getImageOrBuilderList() {
      return image_;
    }
    /**
     * <code>repeated .Image image = 1;</code>
     */
    public int getImageCount() {
      return image_.size();
    }
    /**
     * <code>repeated .Image image = 1;</code>
     */
    public Lifestream.Image getImage(int index) {
      return image_.get(index);
    }
    /**
     * <code>repeated .Image image = 1;</code>
     */
    public Lifestream.ImageOrBuilder getImageOrBuilder(
        int index) {
      return image_.get(index);
    }

    private void initFields() {
      image_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      for (int i = 0; i < getImageCount(); i++) {
        if (!getImage(i).isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < image_.size(); i++) {
        output.writeMessage(1, image_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < image_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, image_.get(i));
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static Lifestream.LifeStream parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Lifestream.LifeStream parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Lifestream.LifeStream parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Lifestream.LifeStream parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Lifestream.LifeStream parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Lifestream.LifeStream parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static Lifestream.LifeStream parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static Lifestream.LifeStream parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static Lifestream.LifeStream parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Lifestream.LifeStream parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(Lifestream.LifeStream prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code LifeStream}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements Lifestream.LifeStreamOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Lifestream.internal_static_LifeStream_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Lifestream.internal_static_LifeStream_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Lifestream.LifeStream.class, Lifestream.LifeStream.Builder.class);
      }

      // Construct using Lifestream.LifeStream.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getImageFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (imageBuilder_ == null) {
          image_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          imageBuilder_.clear();
        }
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Lifestream.internal_static_LifeStream_descriptor;
      }

      public Lifestream.LifeStream getDefaultInstanceForType() {
        return Lifestream.LifeStream.getDefaultInstance();
      }

      public Lifestream.LifeStream build() {
        Lifestream.LifeStream result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Lifestream.LifeStream buildPartial() {
        Lifestream.LifeStream result = new Lifestream.LifeStream(this);
        int from_bitField0_ = bitField0_;
        if (imageBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            image_ = java.util.Collections.unmodifiableList(image_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.image_ = image_;
        } else {
          result.image_ = imageBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Lifestream.LifeStream) {
          return mergeFrom((Lifestream.LifeStream)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Lifestream.LifeStream other) {
        if (other == Lifestream.LifeStream.getDefaultInstance()) return this;
        if (imageBuilder_ == null) {
          if (!other.image_.isEmpty()) {
            if (image_.isEmpty()) {
              image_ = other.image_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureImageIsMutable();
              image_.addAll(other.image_);
            }
            onChanged();
          }
        } else {
          if (!other.image_.isEmpty()) {
            if (imageBuilder_.isEmpty()) {
              imageBuilder_.dispose();
              imageBuilder_ = null;
              image_ = other.image_;
              bitField0_ = (bitField0_ & ~0x00000001);
              imageBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getImageFieldBuilder() : null;
            } else {
              imageBuilder_.addAllMessages(other.image_);
            }
          }
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        for (int i = 0; i < getImageCount(); i++) {
          if (!getImage(i).isInitialized()) {
            
            return false;
          }
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Lifestream.LifeStream parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Lifestream.LifeStream) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // repeated .Image image = 1;
      private java.util.List<Lifestream.Image> image_ =
        java.util.Collections.emptyList();
      private void ensureImageIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          image_ = new java.util.ArrayList<Lifestream.Image>(image_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilder<
          Lifestream.Image, Lifestream.Image.Builder, Lifestream.ImageOrBuilder> imageBuilder_;

      /**
       * <code>repeated .Image image = 1;</code>
       */
      public java.util.List<Lifestream.Image> getImageList() {
        if (imageBuilder_ == null) {
          return java.util.Collections.unmodifiableList(image_);
        } else {
          return imageBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public int getImageCount() {
        if (imageBuilder_ == null) {
          return image_.size();
        } else {
          return imageBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Lifestream.Image getImage(int index) {
        if (imageBuilder_ == null) {
          return image_.get(index);
        } else {
          return imageBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder setImage(
          int index, Lifestream.Image value) {
        if (imageBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureImageIsMutable();
          image_.set(index, value);
          onChanged();
        } else {
          imageBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder setImage(
          int index, Lifestream.Image.Builder builderForValue) {
        if (imageBuilder_ == null) {
          ensureImageIsMutable();
          image_.set(index, builderForValue.build());
          onChanged();
        } else {
          imageBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder addImage(Lifestream.Image value) {
        if (imageBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureImageIsMutable();
          image_.add(value);
          onChanged();
        } else {
          imageBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder addImage(
          int index, Lifestream.Image value) {
        if (imageBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureImageIsMutable();
          image_.add(index, value);
          onChanged();
        } else {
          imageBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder addImage(
          Lifestream.Image.Builder builderForValue) {
        if (imageBuilder_ == null) {
          ensureImageIsMutable();
          image_.add(builderForValue.build());
          onChanged();
        } else {
          imageBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder addImage(
          int index, Lifestream.Image.Builder builderForValue) {
        if (imageBuilder_ == null) {
          ensureImageIsMutable();
          image_.add(index, builderForValue.build());
          onChanged();
        } else {
          imageBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder addAllImage(
          java.lang.Iterable<? extends Lifestream.Image> values) {
        if (imageBuilder_ == null) {
          ensureImageIsMutable();
          super.addAll(values, image_);
          onChanged();
        } else {
          imageBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder clearImage() {
        if (imageBuilder_ == null) {
          image_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          imageBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Builder removeImage(int index) {
        if (imageBuilder_ == null) {
          ensureImageIsMutable();
          image_.remove(index);
          onChanged();
        } else {
          imageBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Lifestream.Image.Builder getImageBuilder(
          int index) {
        return getImageFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Lifestream.ImageOrBuilder getImageOrBuilder(
          int index) {
        if (imageBuilder_ == null) {
          return image_.get(index);  } else {
          return imageBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public java.util.List<? extends Lifestream.ImageOrBuilder> 
           getImageOrBuilderList() {
        if (imageBuilder_ != null) {
          return imageBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(image_);
        }
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Lifestream.Image.Builder addImageBuilder() {
        return getImageFieldBuilder().addBuilder(
            Lifestream.Image.getDefaultInstance());
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public Lifestream.Image.Builder addImageBuilder(
          int index) {
        return getImageFieldBuilder().addBuilder(
            index, Lifestream.Image.getDefaultInstance());
      }
      /**
       * <code>repeated .Image image = 1;</code>
       */
      public java.util.List<Lifestream.Image.Builder> 
           getImageBuilderList() {
        return getImageFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          Lifestream.Image, Lifestream.Image.Builder, Lifestream.ImageOrBuilder> 
          getImageFieldBuilder() {
        if (imageBuilder_ == null) {
          imageBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              Lifestream.Image, Lifestream.Image.Builder, Lifestream.ImageOrBuilder>(
                  image_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          image_ = null;
        }
        return imageBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:LifeStream)
    }

    static {
      defaultInstance = new LifeStream(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:LifeStream)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_Image_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Image_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_LifeStream_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_LifeStream_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020lifestream.proto\"\032\n\005Image\022\021\n\timagedata" +
      "\030\001 \002(\014\"#\n\nLifeStream\022\025\n\005image\030\001 \003(\0132\006.Im" +
      "age"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_Image_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_Image_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_Image_descriptor,
              new java.lang.String[] { "Imagedata", });
          internal_static_LifeStream_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_LifeStream_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_LifeStream_descriptor,
              new java.lang.String[] { "Image", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
