# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: lifestream.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)




DESCRIPTOR = _descriptor.FileDescriptor(
  name='lifestream.proto',
  package='',
  serialized_pb='\n\x10lifestream.proto\"\x1a\n\x05Image\x12\x11\n\timagedata\x18\x01 \x02(\x0c\"#\n\nLifeStream\x12\x15\n\x05image\x18\x01 \x03(\x0b\x32\x06.Image')




_IMAGE = _descriptor.Descriptor(
  name='Image',
  full_name='Image',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='imagedata', full_name='Image.imagedata', index=0,
      number=1, type=12, cpp_type=9, label=2,
      has_default_value=False, default_value="",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=20,
  serialized_end=46,
)


_LIFESTREAM = _descriptor.Descriptor(
  name='LifeStream',
  full_name='LifeStream',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='image', full_name='LifeStream.image', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=48,
  serialized_end=83,
)

_LIFESTREAM.fields_by_name['image'].message_type = _IMAGE
DESCRIPTOR.message_types_by_name['Image'] = _IMAGE
DESCRIPTOR.message_types_by_name['LifeStream'] = _LIFESTREAM

class Image(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _IMAGE

  # @@protoc_insertion_point(class_scope:Image)

class LifeStream(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _LIFESTREAM

  # @@protoc_insertion_point(class_scope:LifeStream)


# @@protoc_insertion_point(module_scope)
