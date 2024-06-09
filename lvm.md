### 使用`LVM`扩容`Vmware`文件系统

1. 关机，`Vmware虚拟机`-`设置`-`硬盘`-`扩展`

2. `fdisk`创建新的分区
   
   ```shell
   # 创建分区
   fdisk /dev/sda
   # 输入 n 多次回车 创建新的分区
   # 输入 w 保存
   ```

3. 使用新分区创建`物理卷(pv)`
   
   ```shell
   pvcreate /dev/sda5
   ```

4. 将新的`物理卷(pv)`添加到原有`卷组(vg)`中
   
   ```shell
   vgextend ubuntu-vg /dev/sda5
   ```
   
   

5. 扩容`逻辑卷(lv)`
   
   ```shell
   # 查看卷组剩余的PE
   vgdisplay ubuntu-vg
   # 扩容
   lvextend -l +2559 /dev/ubuntu-vg/ubuntu-lv
   ```

6. 调整文件系统大小
   
   ```shell
   # ext 类型的文件系统扩容
   resize2fs /dev/ubuntu-vg/ubuntu-lv
   
   # xfs 类型的文件系统
   xfs_growfs /dev/ubuntu-vg/ubuntu-lv
   ```
   
   
