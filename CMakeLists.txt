cmake_minimum_required(VERSION 3.3)
project(IKT433_LAB1_Sockets)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Werror")

set(SOURCE_FILES main.c)
add_executable(IKT433_LAB1_Sockets ${SOURCE_FILES})