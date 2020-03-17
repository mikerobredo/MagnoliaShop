package info.magnolia.training.fullstack.templating.redsysModel;

import javax.jcr.Node;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class RedsysFirstStepModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	public void cleanContext() {
		MgnlContext.removeAttribute("idr", 2);
		MgnlContext.removeAttribute("idi", 2);
		MgnlContext.removeAttribute("proceed", 2);
		MgnlContext.removeAttribute("currency", 2);
		MgnlContext.removeAttribute("merchant", 2);
		MgnlContext.removeAttribute("merchantSignature", 2);
		MgnlContext.removeAttribute("terminal", 2);
	}

	public RedsysFirstStepModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);

	}

	@Override
	public String execute() {

		cleanContext();		
		return super.execute();

	}

}