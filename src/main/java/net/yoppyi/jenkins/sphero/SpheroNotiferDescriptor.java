package net.yoppyi.jenkins.sphero;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.tasks.Ant.AntInstallation;
import hudson.tools.ToolInstallation;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

@Extension
public class SpheroNotiferDescriptor extends BuildStepDescriptor<Publisher> {

	private volatile ModuleInstallation[] installations = new ModuleInstallation[0];

	private String node;
	private String device;

	@Override
	public boolean configure(StaplerRequest req, JSONObject json) throws hudson.model.Descriptor.FormException {

		node = json.getString("node");
		device = json.getString("device");

		save();
		return true;
	}

	public SpheroNotiferDescriptor() {
		super(SpheroNotifer.class);
		load();
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {
		return true;
	}

	@Override
	public String getDisplayName() {
		return "Jenkins Sphro Notifer";
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * Obtains the {@link AntInstallation.DescriptorImpl} instance.
	 */
	public ModuleInstallation.DescriptorImpl getToolDescriptor() {
		return ToolInstallation.all().get(ModuleInstallation.DescriptorImpl.class);
	}

	public ModuleInstallation[] getInstallations() {
		return installations;
	}

	public void setInstallations(ModuleInstallation... antInstallations) {
		this.installations = antInstallations;
		save();
	}

}
