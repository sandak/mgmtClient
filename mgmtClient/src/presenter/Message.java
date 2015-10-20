package presenter;
/**
 * Defines what the Command Message should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class Message extends CommonCommand {

	public Message(Presenter presenter) {
		super(presenter);

	}
	/**
	 *Activates matching methods to send a message to the user.
	 */
	@Override
	public void doCommand(String param) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				presenter.getView().display(param);
				
			}
		}).start();
		

	}

}
