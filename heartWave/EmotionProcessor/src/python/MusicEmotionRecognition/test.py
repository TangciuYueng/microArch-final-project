import h5py

# 使用 h5py 直接打开文件
model_path = 'my_model.h5'
with h5py.File(model_path, 'r') as f:
    # 打印文件中的所有项目
    print("Keys: %s" % f.keys())
    a_group_key = list(f.keys())[0]

    # 打印第一项的所有对象
    print("Items in the first group: %s" % f[a_group_key].items())
