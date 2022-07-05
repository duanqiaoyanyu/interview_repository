
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
