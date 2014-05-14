#! /usr/bin/python

# By Pranav

import lifestream_pb2
import sys


def PromptForImage(image):

  image.imagedata = raw_input("Enter imagedata: ")

  


if len(sys.argv) != 2:
  print "Usage:", sys.argv[0], "IMAGE_FILE"
  sys.exit(-1)

image_book = lifestream_pb2.LifeStream()

# Read the existing image book.
try:
  f = open(sys.argv[1], "rb")
  image_book.ParseFromString(f.read())
  f.close()
except IOError:
  print sys.argv[1] + ": Image not found.  Creating a new image."

# Add the image.
PromptForImage(image_book.image.add())

# Write the new image book back to disk.
f = open(sys.argv[1], "wb")
f.write(image_book.SerializeToString())
f.close()
