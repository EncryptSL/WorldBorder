package com.wimbli.WorldBorder.cmd;

import com.wimbli.WorldBorder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class CmdFillautosave extends WBCmd
{
	public CmdFillautosave()
	{
		name = permission = "fillautosave";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<seconds> - world save interval for Fill.");
		helpText = "Default value: 30 seconds.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		int seconds = Config.FillAutosaveFrequency();
		if (seconds == 0)
		{
			sender.sendMessage(C_HEAD + "World autosave frequency during Fill process is set to 0, disabling it.");
			sender.sendMessage(C_HEAD + "Note that much progress can be lost this way if there is a bug or crash in " +
							   "the world generation process from Bukkit or any world generation plugin you use.");
		}
		else
		{
			sender.sendMessage(C_HEAD + "World autosave frequency during Fill process is set to " + seconds + " seconds (rounded to a multiple of 5).");
			sender.sendMessage(C_HEAD + "New chunks generated by the Fill process will be forcibly saved to disk " +
							   "this often to prevent loss of progress due to bugs or crashes in the world generation process.");
		}
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		int seconds = 0;
		try
		{
			seconds = Integer.parseInt(params.get(0));
			if (seconds < 0)
				throw new NumberFormatException();
		}
		catch(NumberFormatException ex)
		{
			sendErrorAndHelp(sender, "The world autosave frequency must be an integer of 0 or higher. Setting to 0 will disable autosaving of the world during the Fill process.");
			return;
		}

		Config.setFillAutosaveFrequency(seconds);

		if (player != null)
			cmdStatus(sender);
	}
}
