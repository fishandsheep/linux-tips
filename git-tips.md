### git 如何撤回修改

1. 本地修改，未添加到暂存区
  1. 方法一
  ```shell
  # 撤销指定文件的修改
  git checkout -- <file_path>
  
  # 撤销所有文件的修改
  git checkout -- .
  ```
  2. 方法二
  ```shell
  # 撤销指定文件的修改
  git restore <file_path>
  
  # 撤销所有文件的修改
  git restore .
  ```

2. 本地修改，`add` 完成
  ```
  # 取消指定文件的暂存
  git reset HEAD <file_path>
  # 取消所有文件的暂存
  git reset HEAD .
  
  git restore --staged <file_path>  # 指定文件
  git restore --staged .             # 所有文件
  ```

3. 本地修改，`commit` 完成
  ```shell
  # 撤销最近一次提交，修改保留到工作区
  git reset HEAD~1
  
  # 撤销最近一次提交，修改保留到暂存区（相当于回到 add 后状态）
  git reset --soft HEAD~1
  
  # 撤销最近一次提交，且丢弃所有修改
  git reset --hard HEAD~1
  ```

4. 本地修改，`push` 完成
  方法一(安全)
  ```
  # 撤销最近一次推送的提交（生成新提交）
  git revert HEAD
  
  # 撤销指定提交（需提供提交哈希）
  git revert <commit_hash>
  
  # 推送到远程
  git push origin <branch_name>
  ```
  
  方法二（慎用）
  ```shell
  # 1. 本地撤销提交（同情况3）
  git reset --hard HEAD~1  # 丢弃最近一次提交
  
  # 2. 强制推送到远程（覆盖远程历史）
  git push origin <branch_name> --force
  # 或
  git push origin <branch_name> -f
  ```
