##### GNU's Not Unix, create a free, open source operating system(1983) by linus torvalds. Linux distribution(debian[ubuntu] and red hat[centos])
##### command to update and install essential tools in ubuntu and centos
```bash
# for ubuntu
sudo apt update -y && sudo apt full-upgrade -y && sudo apt install build-essential linux-headers-generic dkms -y && sudo apt autoremove && sudo apt autoclean
# ivrtual box Devices > Insert Guest Additions CD Image & execute VBoxLinuxAdditions.run and sudo adduser $USER vboxsf in case facing problem with shared folder mount
```
##### for Mac use UTM instead of VirtualBox.
```sh
# -n do not output the trailing newline/-e enable interpretation of backslash escapes, print working directory, change directory, ~ will be current logged in user home directory
echo "${BASH_VERSION}" && echo -ne "Prajit\tGandhi\n" && pwd && cd ~
```
