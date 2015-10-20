package presenter;
/**
 * Defines what the Command GetData should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class GetData extends CommonCommand {

	public GetData(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates the matching methods of get data request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getModel().getData();

	}

}
