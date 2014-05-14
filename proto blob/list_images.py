#! /usr/bin/python

# By Pranav

import lifestream_pb2
import sys

# Iterates though all images in the ImageBook and prints info about them.
def ListImages(image_book):
  for image in image_book.image:
    print "Image data:", image.imagedata
    
# Main procedure:  Reads the entire image book from a file and prints all
#   the information inside.
if len(sys.argv) != 2:
  print "Usage:", sys.argv[0], "IMAGE_FILE"
  sys.exit(-1)

image_book = lifestream_pb2.LifeStream()

# Read the existing image book.
f = open(sys.argv[1], "rb")
image_book.ParseFromString(f.read())
f.close()

ListImages(image_book)
