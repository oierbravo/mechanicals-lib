package com.oierbravo.mechanicals.foundation.data;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public enum CompatMods {

	AA("actuallyadditions"),
	EIO("enderio"),
	AE2("ae2"),
	ARS_N("ars_nouveau"),
	CFN("create_from_nothing"),
	BR("bigreactors"),
	FN("fluxnetworks"),
	;

	private final String id;

	public boolean reversedMetalPrefix;
	public boolean strippedIsSuffix;
	public boolean omitWoodSuffix;

	private CompatMods(String id) {
		this(id, b -> {
		});
	}

	private CompatMods(String id, Consumer<Builder> props) {
		props.accept(new Builder());
		this.id = id;
	}

	public ResourceLocation ingotOf(String type) {
		return ResourceLocation.fromNamespaceAndPath(id, reversedMetalPrefix ? "ingot_" + type : type + "_ingot");
	}

	public ResourceLocation nuggetOf(String type) {
		return ResourceLocation.fromNamespaceAndPath(id, reversedMetalPrefix ? "nugget_" + type : type + "_nugget");
	}

	public ResourceLocation oreOf(String type) {
		return ResourceLocation.fromNamespaceAndPath(id, reversedMetalPrefix ? "ore_" + type : type + "_ore");
	}

	public ResourceLocation deepslateOreOf(String type) {
		return ResourceLocation.fromNamespaceAndPath(id, reversedMetalPrefix ? "deepslate_ore_" + type : "deepslate_" + type + "_ore");
	}

	public ResourceLocation asResource(String id) {
		return ResourceLocation.fromNamespaceAndPath(this.id, id);
	}

	public String recipeId(String id) {
		return "compat/" + this.id + "/" + id;
	}

	public String getId() {
		return id;
	}

	class Builder {

		Builder reverseMetalPrefix() {
			reversedMetalPrefix = true;
			return this;
		}

		Builder strippedWoodIsSuffix() {
			strippedIsSuffix = true;
			return this;
		}

		Builder omitWoodSuffix() {
			omitWoodSuffix = true;
			return this;
		}

	}

}
