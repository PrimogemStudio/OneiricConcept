package net.mcreator.oneiricconcept.procedures;

import net.mcreator.oneiricconcept.OneiricconceptMod;

public class EightCharactersIndexProcedure {
	public static String execute(double branchIndex, double stemIndex) {
		String heavenlyStems = "";
		String earthlyBranches = "";
		heavenlyStems = "\u7532\u4E59\u4E19\u4E01\u620A\u5DF1\u5E9A\u8F9B\u58EC\u7678";
		earthlyBranches = "\u5B50\u4E11\u5BC5\u536F\u8FB0\u5DF3\u5348\u672A\u7533\u9149\u620C\u4EA5";
		heavenlyStems = heavenlyStems.substring((int) stemIndex, (int) (stemIndex + 1));
		earthlyBranches = earthlyBranches.substring((int) branchIndex, (int) (branchIndex + 1));
		OneiricconceptMod.LOGGER.error(("\u5929\u5E72" + heavenlyStems));
		OneiricconceptMod.LOGGER.error(("\u5730\u652F" + earthlyBranches));
		return heavenlyStems + "" + earthlyBranches;
	}
}
