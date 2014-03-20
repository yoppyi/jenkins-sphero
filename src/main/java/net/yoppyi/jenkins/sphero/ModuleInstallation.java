package net.yoppyi.jenkins.sphero;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
import hudson.Util;
import hudson.model.TaskListener;
import hudson.model.Descriptor;
import hudson.model.Node;
import hudson.remoting.Callable;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstaller;
import hudson.tools.ToolProperty;
import hudson.tools.ToolInstallation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jenkins.model.Jenkins;

import org.kohsuke.stapler.DataBoundConstructor;

public class ModuleInstallation extends ToolInstallation {

	private static final long serialVersionUID = -5795401391333789727L;

	@DataBoundConstructor
	public ModuleInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
		super(name, home, properties);
	}

	@Override
	public Descriptor<ToolInstallation> getDescriptor() {
		// TODO Auto-generated method stub
		return super.getDescriptor();
	}

	/**
	 * Gets the executable path of this Ant on the given target system.
	 */
	public File getModuleDir(Launcher launcher) throws IOException, InterruptedException {
		return launcher.getChannel().call(new Callable<File, IOException>() {
			private static final long serialVersionUID = 1L;

			public File call() throws IOException {
				String home = Util.replaceMacro(getHome(), EnvVars.masterEnvVars);
				File dir = new File(home, "node_modules");
				if (dir.exists()) {
					return dir;
				}
				return null;
			}
		});
	}

	public ModuleInstallation forEnvironment(EnvVars environment) {
		return new ModuleInstallation(getName(), environment.expand(getHome()), getProperties().toList());
	}

	public ModuleInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
		return new ModuleInstallation(getName(), translateFor(node, log), getProperties().toList());
	}

	@Extension
	public static class DescriptorImpl extends ToolDescriptor<ModuleInstallation> {

		@Override
		public List<? extends ToolInstaller> getDefaultInstallers() {
			return Collections.singletonList(new ModuleInstaller("Default"));
		}

		@Override
		public String getDisplayName() {
			// TODO Auto-generated method stub
			return "Sphero Jenkins Node Module";
		}

		// for compatibility reasons, the persistence is done by Ant.DescriptorImpl
		@Override
		public ModuleInstallation[] getInstallations() {
			return Jenkins.getInstance().getDescriptorByType(SpheroNotiferDescriptor.class).getInstallations();
		}

		@Override
		public void setInstallations(ModuleInstallation... installations) {
			Jenkins.getInstance().getDescriptorByType(SpheroNotiferDescriptor.class).setInstallations(installations);
		}
	}
}
