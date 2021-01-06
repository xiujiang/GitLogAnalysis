package com.gitLog;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GitUtil {
	private Git git;// git对象
	private String userName;// 资源库账户
	private String pwd;// 密码
	private String giturl;// git地址
	private String pjName;// 项目名
	private String branch;// 分支名

	public GitUtil(String userName, String pwd, String giturl, String pjName, String branch) throws Exception {

		this.userName = userName;
		this.pwd = pwd;
		this.giturl = giturl;
		this.pjName = pjName;
		this.branch = branch;
		// 如果是空，则默认使用master分支
		if (null == branch || "".equals(branch)) {
			branch = "master";
		}
		// 初始化一个git
		initGit();
	}


	/**
	 * @方法简介:初始化一个GIT对象
	 */
	public void initGit() throws Exception {
		// 如果目录存在就先更新后open
		if (new File("/Users/joel/IdeaProjects2/gitLog" + File.separator + this.pjName).exists()) {
			this.git = Git.open(new File("/Users/joel/IdeaProjects2/gitLog" + File.separator + this.pjName));
			FetchResult resule = this.git.fetch()// 如果已经存在该库，就使用fetch刷新一下
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.userName, this.pwd))//远程登录git的凭证
					.setCheckFetchedObjects(true)
					.call();
			if (resule == null) {
				throw new Exception("获取的Git对象已失效");
			}
			// 切换分支
			git.checkout().setCreateBranch(false).setName(branch).call();
			// 查看当前分支
			//System.out.println(git.getRepository().getBranch());
		} else {
			// 如果不存在就clone
			this.git = Git.cloneRepository().setURI(this.giturl)
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.userName, this.pwd))
					.setCloneAllBranches(true)//获取所有分支
					.setDirectory(new File("/Users/joel/IdeaProjects2/gitLog" + File.separator + this.pjName))//指定本地clone库
					.call();
			// 查看所clone下的项目的所有分支
			int c = 0;
			List<Ref> call = this.git.branchList().call();
			for (Ref ref : call) {
				System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
				c++;
			}
			System.out.println("Number of branches: " + c);
		}
	}

	/**
	 * @方法简介: 获取所有的版本号与版本号对应的时间戳
	 */
	public ArrayList<HashMap<String, Object>> getGitVersion()
			throws NoHeadException, GitAPIException, Exception {
		Iterable<RevCommit> logIterable = this.git.log().call();
		Iterator<RevCommit> logIterator = logIterable.iterator();//获取所有版本号的迭代器

		if (logIterator == null) {
			throw new Exception("该项目暂无日志记录");
		}
		int row = 0;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while (logIterator.hasNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			RevCommit commit = logIterator.next();
			Date commitDate = commit.getAuthorIdent().getWhen();     //提交时间
			String commitPerson = commit.getAuthorIdent().getName() ;    //提交人
			String commitID = commit.getName();    //提交的版本号（之后根据这个版本号去获取对应的详情记录）
			map.put("version", commitID);
			map.put("commitDate", commitDate);
			map.put("commitPerson", commitPerson);
			list.add(row, map);
			row++;
		}
		return list;
	}


	/**
	 * @方法简介 :根据指定版本号获取版本号下面的详情记录
	 *
	 */
	@SuppressWarnings("resource")
	public HashMap<String, Object> getCommitLogList(String revision) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 通过git获取项目库
		Repository repository = this.git.getRepository();
		// 根据所需要查询的版本号查新ObjectId
		ObjectId objId = repository.resolve(revision);

		//根据版本号获取指定的详细记录
		Iterable<RevCommit> allCommitsLater = git.log().add(objId).call();
		if (allCommitsLater == null) {
			throw new Exception("该提交版本下无当前查询用户的提交记录");
		}
		Iterator<RevCommit> iter = allCommitsLater.iterator();
		RevCommit commit = iter.next();//提交对象
		Date commitDate = commit.getAuthorIdent().getWhen();//提交时间
		map.put("commitDate", commitDate);
		String commitPerson = commit.getAuthorIdent().getName();//提交人
		map.put("commitPerson", commitPerson);
		String message = commit.getFullMessage();//提交日志
		map.put("message", message);
		// 给存储库创建一个树的遍历器
		TreeWalk tw = new TreeWalk(repository);
		// 将当前commit的树放入树的遍历器中
		tw.addTree(commit.getTree());
		commit = iter.next();
		if (commit != null) {
			tw.addTree(commit.getTree());
		} else {
			return null;
		}
		tw.setRecursive(true);
		RenameDetector rd = new RenameDetector(repository);
		rd.addAll(DiffEntry.scan(tw));
		//获取到详细记录结果集    在这里就能获取到一个版本号对应的所有提交记录（详情！！！）
		List<DiffEntry> list = rd.compute();
		for (DiffEntry diffEntry : list) {
			System.out.println(diffEntry.toString());
		}

		map.put("list", list);
		return map;
	}

	public void diffMethod(String oldP, String headP){
		Repository repository=git.getRepository();
		ObjectReader reader = repository.newObjectReader();
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

		try {
			ObjectId old = repository.resolve(oldP);
			ObjectId head = repository.resolve(headP);
			oldTreeIter.reset(reader, old);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);
			List<DiffEntry> diffs= git.diff()
					.setNewTree(newTreeIter)
					.setOldTree(oldTreeIter)
					.call();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter df = new DiffFormatter(out);
			df.setRepository(git.getRepository());

			for (DiffEntry diffEntry : diffs) {
				df.format(diffEntry);
				String diffText = out.toString("UTF-8");
				System.out.println(diffText);
				//  out.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}