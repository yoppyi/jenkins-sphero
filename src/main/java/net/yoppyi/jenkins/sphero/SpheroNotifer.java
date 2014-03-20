package net.yoppyi.jenkins.sphero;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.kohsuke.stapler.DataBoundConstructor;

public class SpheroNotifer extends Notifier {

	private final String moduleName;
	private final String success;
	private final String unstable;
	private final String failure;
	private final String notBuilt;
	private final String aborted;

	@DataBoundConstructor
	public SpheroNotifer(String moduleName, String success, String unstable, String failure, String notBuilt,
			String aborted) {
		super();
		this.moduleName = moduleName;
		this.success = success;
		this.unstable = unstable;
		this.failure = failure;
		this.notBuilt = notBuilt;
		this.aborted = aborted;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getSuccess() {
		return success;
	}

	public String getUnstable() {
		return unstable;
	}

	public String getFailure() {
		return failure;
	}

	public String getNotBuilt() {
		return notBuilt;
	}

	public String getAborted() {
		return aborted;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
			throws InterruptedException, IOException {
		SpheroNotiferDescriptor descriptor = getDescriptor();

		Node node = Computer.currentComputer().getNode();
		EnvVars env = build.getEnvironment(listener);
		env.overrideAll(build.getBuildVariables());

		ModuleInstallation mi = getModule();
		File dir = null;
		if (mi != null) {
			mi = mi.forNode(node, listener);
			mi = mi.forEnvironment(env);
			dir = mi.getModuleDir(launcher);
			if (dir == null) {
				// TODO message
				listener.fatalError("Node Moduleが見つかりません。");
				return false;
			}
			env.put("NODE_PATH", dir.getAbsolutePath());
		} else {
			return false;
		}

		FilePath root = node.getRootPath();
		if (root == null) {
			throw new IllegalArgumentException("Node " + node.getDisplayName() + " seems to be offline");
		}

		StringBuilder sb = new StringBuilder();
		String script = aborted;
		String file = "aborted.js";

		if (build.getResult() == Result.SUCCESS) {
			script = success;
			file = "success.js";
		} else if (build.getResult() == Result.UNSTABLE) {
			script = unstable;
			file = "unstable.js";
		} else if (build.getResult() == Result.FAILURE) {
			script = failure;
			file = "failure.js";
		} else if (build.getResult() == Result.NOT_BUILT) {
			script = notBuilt;
			file = "notBuilt.js";
		} else if (build.getResult() == Result.ABORTED) {
			script = aborted;
			file = "aborted.js";
		}

		if (script == null || script.trim().length() == 0) {
			try (InputStream is = SpheroNotifer.class.getResourceAsStream(("SpheroNotifer/" + file));
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);) {
				while (br.ready()) {
					sb.append(br.readLine());
					sb.append(System.getProperty("line.separator"));
				}
			}
		} else {
			sb.append(script.replaceAll("\r?\n", System.getProperty("line.separator")));
		}
		FilePath temp = new FilePath(dir).createTextTempFile("notifer", "js", sb.toString());
		try {
			int r = launcher
					.launch()
					.envs(env)
					.cmds("node", new File(temp.toURI()).getAbsolutePath(), descriptor.getDevice(),
							String.valueOf(build.getResult().toString())).join();
			if (r != 0) {
				throw new IOException("Command returned status " + r);
			}
		} finally {
			temp.delete();
		}

		return true;
	}

	@Override
	public SpheroNotiferDescriptor getDescriptor() {
		return (SpheroNotiferDescriptor) super.getDescriptor();
	}

	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}

	public ModuleInstallation getModule() {
		for (ModuleInstallation i : getDescriptor().getInstallations()) {
			if (moduleName != null && moduleName.equals(i.getName()))
				return i;
		}
		return null;
	}
}
