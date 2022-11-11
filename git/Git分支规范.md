
"<<<<<<<"<p>
// 这里是本地改动的代码<p>
"======="<p>
// 这里是远程的或者其他分支的代码<p>
">>>>>>>"<p>
Everything between <<<<<<< and ======= are your local changes. These changes are not in the remote repository yet. 
All the lines between ======= and >>>>>>> are the changes from the remote repository or another branch. 
Now you need to look into these two sections and make a decision.


GitFlow 流程
1. develop 从 main 中切出来
2. release 从 develop 分支切出来
3. feature 分支从 develop 分支切出来
4. feature 分支一旦开发完成合并(pull request) 到 release 分支
5. release 分支一旦发布, 需要同时合并(pull request)到 develop 分支、master 分支
6. 一旦 main 上发现了 bug, 需要从 main 上切出一个 hotfix 分支
7. hotfix 分支修复完, 需要同时合并(pull request)到 develop 分支、main 分支


注意: 其中 pull request 合并操作通常是 merge 的形式, 然后可能会产生冲突。如果发生冲突,
就需要解决好冲突然后再进行分支合并, 解决冲突的流程通常是如下流程。比如我现在是 feature 分支 合并到 develop
1. 发起 pull request base:develop <- head:feature
2. 结果发现冲突, 这个时候不要紧, 依旧是可以创建 pull request 请求的
3. 然后告诉你无法自动合并, 有冲突的存在。
4. 然后接下来是标准流程。先切换到 develop 分支, 然后拉去最新代码 git fetch -a; git rebase
5. 然后再切换到 feature 分支, 把 develop 合并到 feature 中, git rebase develop
6. 然后此时肯定会冲突, 然后解决好冲突。解决完冲突 git add .; git commit; 然后会进入到 vim 编辑器, 然后直接退出:q
7. 此时冲突已经解决完毕, 提交代码 git push; 然后再去 pull request 界面中, 完成后续的合并操作, 此时不出意外都是可以自动合并的。

参考资料:
- https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow
- https://github.com/firstcontributions/first-contributions/tree/master/additional-material/git_workflow_scenarios
- 




#### git revert
可以把某个 commit 的变更, 透过『相反』的步骤把变更给还原.
操作流程
1. git revert commitId
2. 有没有冲突, 如果有冲突解决冲突
3. git commit 建立一个新版本提交

**使用 git revert 命令套用变更, 但不执行 commit 动作
使用 git revert 时, 预设若执行成功, 会直接建立一个 commit 版本, 如果你希望在执行 git revert 之后先保留变更的内容, 也许再添加一些档案或修改一些内容, 然后再资兴签入的话, 可以使用以下步骤.
1. git revert -n commitId
2. 这个时候, 索引状态已经被更新, 但你还是可以继续修改这个版本, 直到你想完成本次动作. 这时你有两个执行的选项
   - git revert --continue 代表你已经完成所有操作, 并且建立一个新版本, 就跟执行 git commit 一样
   - git revert --abort 代表你准备放弃这次复原的动作, 执行这个命令会让所有变更状态还原, 也就是删除的档案又会被加回来.

> 请注意: 当 git revert -n 执行完后, 并不是用 git commit 建立版本喔!

小结:
今天介绍的『还原』版本的机制, 其实是透过『新增一个版本』的方式把变更的内容改回来, 而且透过这种方式, 你可以透过版本历史记录中明确找出你到底时针对哪几个版本进行还原的. 另外就是这个『还原』的过程, 其实跟『合并』的过程非常类似, 发生冲突时的解决方法也都如出一辙.
