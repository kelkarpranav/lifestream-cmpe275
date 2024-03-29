// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: lifestream.proto

#ifndef PROTOBUF_lifestream_2eproto__INCLUDED
#define PROTOBUF_lifestream_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2005000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_lifestream_2eproto();
void protobuf_AssignDesc_lifestream_2eproto();
void protobuf_ShutdownFile_lifestream_2eproto();

class Image;
class LifeStream;

// ===================================================================

class Image : public ::google::protobuf::Message {
 public:
  Image();
  virtual ~Image();

  Image(const Image& from);

  inline Image& operator=(const Image& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const Image& default_instance();

  void Swap(Image* other);

  // implements Message ----------------------------------------------

  Image* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const Image& from);
  void MergeFrom(const Image& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required bytes imagedata = 1;
  inline bool has_imagedata() const;
  inline void clear_imagedata();
  static const int kImagedataFieldNumber = 1;
  inline const ::std::string& imagedata() const;
  inline void set_imagedata(const ::std::string& value);
  inline void set_imagedata(const char* value);
  inline void set_imagedata(const void* value, size_t size);
  inline ::std::string* mutable_imagedata();
  inline ::std::string* release_imagedata();
  inline void set_allocated_imagedata(::std::string* imagedata);

  // @@protoc_insertion_point(class_scope:Image)
 private:
  inline void set_has_imagedata();
  inline void clear_has_imagedata();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::std::string* imagedata_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(1 + 31) / 32];

  friend void  protobuf_AddDesc_lifestream_2eproto();
  friend void protobuf_AssignDesc_lifestream_2eproto();
  friend void protobuf_ShutdownFile_lifestream_2eproto();

  void InitAsDefaultInstance();
  static Image* default_instance_;
};
// -------------------------------------------------------------------

class LifeStream : public ::google::protobuf::Message {
 public:
  LifeStream();
  virtual ~LifeStream();

  LifeStream(const LifeStream& from);

  inline LifeStream& operator=(const LifeStream& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const LifeStream& default_instance();

  void Swap(LifeStream* other);

  // implements Message ----------------------------------------------

  LifeStream* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const LifeStream& from);
  void MergeFrom(const LifeStream& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // repeated .Image image = 1;
  inline int image_size() const;
  inline void clear_image();
  static const int kImageFieldNumber = 1;
  inline const ::Image& image(int index) const;
  inline ::Image* mutable_image(int index);
  inline ::Image* add_image();
  inline const ::google::protobuf::RepeatedPtrField< ::Image >&
      image() const;
  inline ::google::protobuf::RepeatedPtrField< ::Image >*
      mutable_image();

  // @@protoc_insertion_point(class_scope:LifeStream)
 private:

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::google::protobuf::RepeatedPtrField< ::Image > image_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(1 + 31) / 32];

  friend void  protobuf_AddDesc_lifestream_2eproto();
  friend void protobuf_AssignDesc_lifestream_2eproto();
  friend void protobuf_ShutdownFile_lifestream_2eproto();

  void InitAsDefaultInstance();
  static LifeStream* default_instance_;
};
// ===================================================================


// ===================================================================

// Image

// required bytes imagedata = 1;
inline bool Image::has_imagedata() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void Image::set_has_imagedata() {
  _has_bits_[0] |= 0x00000001u;
}
inline void Image::clear_has_imagedata() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void Image::clear_imagedata() {
  if (imagedata_ != &::google::protobuf::internal::kEmptyString) {
    imagedata_->clear();
  }
  clear_has_imagedata();
}
inline const ::std::string& Image::imagedata() const {
  return *imagedata_;
}
inline void Image::set_imagedata(const ::std::string& value) {
  set_has_imagedata();
  if (imagedata_ == &::google::protobuf::internal::kEmptyString) {
    imagedata_ = new ::std::string;
  }
  imagedata_->assign(value);
}
inline void Image::set_imagedata(const char* value) {
  set_has_imagedata();
  if (imagedata_ == &::google::protobuf::internal::kEmptyString) {
    imagedata_ = new ::std::string;
  }
  imagedata_->assign(value);
}
inline void Image::set_imagedata(const void* value, size_t size) {
  set_has_imagedata();
  if (imagedata_ == &::google::protobuf::internal::kEmptyString) {
    imagedata_ = new ::std::string;
  }
  imagedata_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* Image::mutable_imagedata() {
  set_has_imagedata();
  if (imagedata_ == &::google::protobuf::internal::kEmptyString) {
    imagedata_ = new ::std::string;
  }
  return imagedata_;
}
inline ::std::string* Image::release_imagedata() {
  clear_has_imagedata();
  if (imagedata_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = imagedata_;
    imagedata_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void Image::set_allocated_imagedata(::std::string* imagedata) {
  if (imagedata_ != &::google::protobuf::internal::kEmptyString) {
    delete imagedata_;
  }
  if (imagedata) {
    set_has_imagedata();
    imagedata_ = imagedata;
  } else {
    clear_has_imagedata();
    imagedata_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// -------------------------------------------------------------------

// LifeStream

// repeated .Image image = 1;
inline int LifeStream::image_size() const {
  return image_.size();
}
inline void LifeStream::clear_image() {
  image_.Clear();
}
inline const ::Image& LifeStream::image(int index) const {
  return image_.Get(index);
}
inline ::Image* LifeStream::mutable_image(int index) {
  return image_.Mutable(index);
}
inline ::Image* LifeStream::add_image() {
  return image_.Add();
}
inline const ::google::protobuf::RepeatedPtrField< ::Image >&
LifeStream::image() const {
  return image_;
}
inline ::google::protobuf::RepeatedPtrField< ::Image >*
LifeStream::mutable_image() {
  return &image_;
}


// @@protoc_insertion_point(namespace_scope)

#ifndef SWIG
namespace google {
namespace protobuf {


}  // namespace google
}  // namespace protobuf
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_lifestream_2eproto__INCLUDED
