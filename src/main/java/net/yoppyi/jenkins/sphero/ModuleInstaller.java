package net.yoppyi.jenkins.sphero;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.TaskListener;
import hudson.model.Node;
import hudson.tools.ToolInstaller;
import hudson.tools.ToolInstallerDescriptor;
import hudson.tools.ToolInstallation;

import java.io.File;
import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;

public class ModuleInstaller extends ToolInstaller {

	@DataBoundConstructor
	public ModuleInstaller(String label) {
		super(label);
	}

	@Override
	public FilePath performInstallation(ToolInstallation tool, Node node, TaskListener log) throws IOException,
			InterruptedException {
		FilePath dir = preferredLocation(tool, node);
		File file = new File(dir.toURI());

		FilePath script = null;
		if (!isUpToDate(dir)) {
			try {
				script = dir.createTextTempFile("node_module_install", ".bat", "npm install spheron -g --prefix "
						+ file.getAbsoluteFile());
				// FIXME nix
				String[] cmd = { script.getRemote() };
				int r = node.createLauncher(log).launch().cmds(cmd).stdout(log).pwd(dir).join();
				if (r != 0) {
					throw new IOException("Command returned status " + r);
				}
			} finally {
				if (script != null) {
					script.delete();
				}
			}
		}
		return dir;
	}

	protected boolean isUpToDate(FilePath expectedLocation) throws IOException, InterruptedException {
		FilePath marker = expectedLocation.child("node_modules");
		return marker.exists();
	}

	public String getToolHome() {
		return ModuleInstaller.class.getPackage().getName();
	}

	@Extension
	public static final class DescriptorImpl extends ToolInstallerDescriptor<ModuleInstaller> {
		public String getDisplayName() {
			return "install by npm.";
		}

		@Override
		public boolean isApplicable(Class<? extends ToolInstallation> toolType) {
			return toolType == ModuleInstallation.class;
		}

	}
}
