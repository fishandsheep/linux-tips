# `ubuntu`个人设置

0. 安装基础必要的软件
```shell
  sudo apt update
  sudo apt upgrade
  sudo apt install build-essential
  sudo apt install git zsh curl
  # 安装 edge ,登陆 edge , 设置为 默认浏览器软件
```

1. 主目录下的文件夹设置为英文
```shell
  export LANG=en_US
  xdg-user-dirs-gtk-update
```
  或者直接将中文目录删掉即可

2. 安装配置 clash
```shell
  # 1. https://devpn.github.io/docs/start/ubuntu/clash/
  # 根据教程安装
  # 2. https://lce.halsh.cloud/#/dashboard
  # 订阅clash
  # 3. 设置系统代理，局域网连接，测试
```
3. 安装 `zsh` `oh-my-zsh`
```shell
  # 设置zsh为默认的shell
  sudo chsh -s $(which zsh)
  # 注销用户
```

4. 安装 `lazyvim`
```shell
  # 下载 nerdfonts https://www.nerdfonts.com/font-downloads 配置 JetBrainMono
  mkdir ~/.fonts/JetBrainsMonoNerdFontMono-Regular.ttf
  fc-cache -fv
  # 配置 terminal 配置文件-文本-自定义字体-...

```
