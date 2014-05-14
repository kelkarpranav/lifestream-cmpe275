# By Pranav


.PHONY: all cpp java python clean

all: cpp java python

cpp:    add_image_cpp    list_images_cpp
java:   add_image_java   list_images_java
python: add_image_python list_images_python

clean:
	rm -f add_image_cpp list_images_cpp add_image_java list_images_java add_image_python list_images_python
	rm -f javac_middleman AddImage*.class ListImages*.class com/example/tutorial/*.class
	rm -f protoc_middleman imagebook.pb.cc imagebook.pb.h imagebook_pb2.py com/example/tutorial/LifeStreamProtos.java
	rm -f *.pyc
	rmdir com/example/tutorial 2>/dev/null || true
	rmdir com/example 2>/dev/null || true
	rmdir com 2>/dev/null || true

protoc_middleman: lifestream.proto
	protoc --cpp_out=. --java_out=. --python_out=. lifestream.proto
	@touch protoc_middleman

add_image_cpp: add_image.cc protoc_middleman
	pkg-config --cflags protobuf  # fails if protobuf is not installed
	c++ add_image.cc imagebook.pb.cc -o add_image_cpp `pkg-config --cflags --libs protobuf`

list_images_cpp: list_images.cc protoc_middleman
	pkg-config --cflags protobuf  # fails if protobuf is not installed
	c++ list_images.cc imagebook.pb.cc -o list_images_cpp `pkg-config --cflags --libs protobuf`

javac_middleman: AddImage.java ListImages.java protoc_middleman
	javac AddImage.java ListImages.java com/example/tutorial/LifeStreamProtos.java
	@touch javac_middleman

add_image_java: javac_middleman
	@echo "Writing shortcut script add_image_java..."
	@echo '#! /bin/sh' > add_image_java
	@echo 'java -classpath .:$$CLASSPATH AddImage "$$@"' >> add_image_java
	@chmod +x add_image_java

list_images_java: javac_middleman
	@echo "Writing shortcut script list_images_java..."
	@echo '#! /bin/sh' > list_images_java
	@echo 'java -classpath .:$$CLASSPATH ListImages "$$@"' >> list_images_java
	@chmod +x list_images_java

add_image_python: add_image.py protoc_middleman
	@echo "Writing shortcut script add_image_python..."
	@echo '#! /bin/sh' > add_image_python
	@echo './add_image.py "$$@"' >> add_image_python
	@chmod +x add_image_python

list_images_python: list_images.py protoc_middleman
	@echo "Writing shortcut script list_images_python..."
	@echo '#! /bin/sh' > list_images_python
	@echo './list_images.py "$$@"' >> list_images_python
	@chmod +x list_images_python
