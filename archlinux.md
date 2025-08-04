1. 配置虚拟机UEFI启动：虚拟机-选项-高级-UEFI

2. 验证系统的引导模式
   
   ```shell
   cat /sys/firmware/efi/fw_platform_size
   ```
   
   

3. 设置字号(可选)
   
   ```shell
   setfont ter-12b
   ```

4. 连接网络，验证
   
   ```shell
   ip link
   ping bing.com
   ```

5. 创建分区
   
   ```shell
   # 查看分区信息
   fdisk -l
   # 分区 
   cfdisk /dev/要被分区的磁盘
   # 引导分区 /boot 1G
   # 交换分区 4G
   # 剩余分区
   ```

6. 格式化分区
   
   ```shell
   mkfs.ext4 /dev/root_partition（根分区）
   mkfs.fat -F 32 /dev/efi_system_partition（EFI 系统分区）
   mkswap /dev/swap_partition（交换空间分区）
   ```

7. 挂载分区
   
   ```shell
   mount /dev/root_partition（根分区） /mnt
   mount --mkdir /dev/efi_system_partition /mnt/boot
   swapon /dev/swap_partition（交换空间分区）
   ```

8. 下载配置源
   
   ```shell
   curl -L 'https://archlinux.org/mirrorlist/?country=CN&protocol=https' -o /etc/pacman.d/mirrorlist
   # 编辑文件 取消注释
   ```

9. 安装linux 基础软件、linux内核等
   
   ```shell
   pacstrap -K /mnt base linux git vim grub efibootmgr
   ```
   
   -----

10. 配置系统
    
    ```shell
    # 生成fstab 文件
    genfstab -U /mnt > /mnt/etc/fstab
    # 进入新系统
    arch-chroot /mnt
    # 设置时间和时区
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
    hwclock --systohc
    # 编辑 /etc/locale.gen 取消 en_US zh_CN UTF-8
    /etc/locale.conf # 创建文件
    LANG=en_US.UTF-8 # 添加区域设置
    # 创建hostname
    /etc/hostname # 添加主机名
    ```

11. 安装引导程序
    
    ```shell
    grub-install --target=x86_64-efi --efi-directory=/boot --bootloader-id=GRUB
     grub-mkconfig -o /boot/grub/grub.cfg
    ```

12. 重启计算机
    
    ```shell
    umount -R /mnt
    reboot
    ```
    
    

13. 配置网络
    
    ```shell
    #查看网卡
    ip link 
    #开启网卡
    ip link set ens33 up
    
    systemctl status systemd-networkd.service
    systemctl start systemd-networkd.service
    systemctl status systemd-networkd.service
    cp /usr/lib/systemd/network/89-ethernet.network.example /etc/systemd/network/89-ethernet.network
    systemctl restart systemd-networkd.service
    systemctl status systemd-networkd.service
    
    systemctl status systemd-resolved.service
    systemctl start systemd-resolved.service
    systemctl status systemd-resolved.service
    
    systemctl enable systemd-networkd.service
    systemctl enable systemd-resolved.service
    ```
    
    
