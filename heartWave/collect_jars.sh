#!/bin/bash

# 创建名为jars的新文件夹
mkdir -p ./jars

# 搜索当前目录下的所有文件夹
for dir in */; do
  if [ -d "$dir" ]; then
    # 如果文件夹中有target文件夹
    if [ -d "${dir}target" ]; then
      # 拷贝target文件夹中的*jar文件到jars文件夹中
      cp -n "${dir}target"/*.jar ./jars
    fi
  fi
done