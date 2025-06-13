package com.oierbravo.mechanicals.utility;

import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.*;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MechanicalRegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
    private final boolean addItems;

    private Collection<RegistryEntry<Item, Item>> items = List.of();
    private Collection<RegistryEntry<Block, Block>> blocks = List.of();
    private Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>> simpleFactories = Map.of();
    private Map<ItemProviderEntry<?, ?>, CreativeModeTab.TabVisibility> simpleVisibilities = Map.of();
    private Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> simpleBeforeOrderings = Map.of();
    private Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> simpleAfterOrderings = Map.of();
    private List<ItemProviderEntry<?, ?>> simpleExclusions = List.of();
    private List<ItemEntry<TagDependentIngredientItem>> tagDependentExclusion = List.of();

    private Consumer<List<ItemOrdering>> orderingsPost;
    private Consumer<Map<Item, CreativeModeTab.TabVisibility>> visibilitiesPost;

    private static final Predicate<Item> IS_ITEM_3D_PREDICATE;

    static {
        MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
        if (CatnipServices.PLATFORM.getEnv().isClient())
            isItem3d.setValue(makeClient3dItemPredicate());
        IS_ITEM_3D_PREDICATE = isItem3d.getValue();
    }

    protected MechanicalRegistrateDisplayItemsGenerator(boolean addItems) {
        this.addItems = addItems;
    }
    public static MechanicalRegistrateDisplayItemsGenerator create(boolean addItems){
        return new MechanicalRegistrateDisplayItemsGenerator(addItems);
    }
    @OnlyIn(Dist.CLIENT)
    private static Predicate<Item> makeClient3dItemPredicate() {
        return item -> {
            ItemRenderer itemRenderer = Minecraft.getInstance()
                    .getItemRenderer();
            BakedModel model = itemRenderer.getModel(new ItemStack(item), null, null, 0);
            return model.isGui3d();
        };
    }
    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        Predicate<Item> exclusionPredicate = makeExclusionPredicate();
        List<ItemOrdering> orderings = makeOrderings();
        Function<Item, ItemStack> stackFunc = makeStackFunc();
        Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();

        List<Item> items = new LinkedList<>();
        if (addItems) {
            items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
        }
        items.addAll(collectBlocks(exclusionPredicate));
        if (addItems) {
            items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));
        }

        applyOrderings(items, orderings);
        outputAll(output, items, stackFunc, visibilityFunc);
    }

    private List<ItemOrdering> makeOrderings() {
        List<ItemOrdering> orderings = new ReferenceArrayList<>();

        this.simpleBeforeOrderings.forEach((entry, otherEntry) -> {
            orderings.add(ItemOrdering.before(entry.asItem(), otherEntry.asItem()));
        });

        this.simpleAfterOrderings.forEach((entry, otherEntry) -> {
            orderings.add(ItemOrdering.after(entry.asItem(), otherEntry.asItem()));
        });
        if(orderingsPost != null)
            orderingsPost.accept(orderings);

        return orderings;
    }

    private Function<Item, ItemStack> makeStackFunc() {
        Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();


        this.simpleFactories.forEach((entry, factory) -> {
            factories.put(entry.asItem(), factory);
        });

        return item -> {
            Function<Item, ItemStack> factory = factories.get(item);
            if (factory != null) {
                return factory.apply(item);
            }
            return new ItemStack(item);
        };
    }



    private Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
        Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

        this.simpleVisibilities.forEach((entry, factory) -> {
            visibilities.put(entry.asItem(), factory);
        });
        if(visibilitiesPost != null)
            visibilitiesPost.accept(visibilities);

        return item -> {
            CreativeModeTab.TabVisibility visibility = visibilities.get(item);
            if (visibility != null) {
                return visibility;
            }
            return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
        };
    }
    private void applyOrderings(List<Item> items, List<ItemOrdering> orderings) {
        for (ItemOrdering ordering : orderings) {
            int anchorIndex = items.indexOf(ordering.anchor());
            if (anchorIndex != -1) {
                Item item = ordering.item();
                int itemIndex = items.indexOf(item);
                if (itemIndex != -1) {
                    items.remove(itemIndex);
                    if (itemIndex < anchorIndex) {
                        anchorIndex--;
                    }
                }
                if (ordering.type() == ItemOrdering.Type.AFTER) {
                    items.add(anchorIndex + 1, item);
                } else {
                    items.add(anchorIndex, item);
                }
            }
        }
    }


    private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Item, Item> entry : this.items) {
            Item item = entry.get();
            if (item instanceof BlockItem)
                continue;
            if (!exclusionPredicate.test(item))
                items.add(item);
        }
        return items;
    }


    private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Block, Block> entry : this.blocks) {
            Item item = entry.get()
                    .asItem();
            if (item == Items.AIR)
                continue;
            if (!exclusionPredicate.test(item))
                items.add(item);
        }
        items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
        return items;
    }

    private Predicate<Item> makeExclusionPredicate() {
        Set<Item> exclusions = new ReferenceOpenHashSet<>();

        for (ItemProviderEntry<?, ?> entry : this.simpleExclusions) {
            exclusions.add(entry.asItem());
        }

        for (ItemEntry<TagDependentIngredientItem> entry : this.tagDependentExclusion) {
            TagDependentIngredientItem item = entry.get();
            if (item.shouldHide()) {
                exclusions.add(entry.asItem());
            }
        }

        return exclusions::contains;
    }
    private void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, ItemStack> stackFunc, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
        for (Item item : items) {
            output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
        }
    }
    public MechanicalRegistrateDisplayItemsGenerator withItems(Collection<RegistryEntry<Item, Item>> items){
        this.items = items;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withItems(Supplier<Collection<RegistryEntry<Item, Item>>> items){
        this.items = items.get();
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withBlocks(Collection<RegistryEntry<Block, Block>> blocks){
        this.blocks = blocks;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withBlocks(Supplier<Collection<RegistryEntry<Block, Block>>> blocks){
        this.blocks = blocks.get();
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withFactories(Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>> factories){
        this.simpleFactories = factories;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withFactories(Supplier<Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>>> factories){
        this.simpleFactories = factories.get();
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withVisibilities(Map<ItemProviderEntry<?, ?>, CreativeModeTab.TabVisibility> visibilities){
        this.simpleVisibilities = visibilities;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withVisibilities(Supplier<Map<ItemProviderEntry<?, ?>, CreativeModeTab.TabVisibility>> visibilities){
        this.simpleVisibilities = visibilities.get();
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withVisibilitiesPost(Consumer<Map<Item, CreativeModeTab.TabVisibility>> visibilities){
        this.visibilitiesPost = visibilities;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withBeforeOrderings(Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> orderings){
        this.simpleBeforeOrderings = orderings;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withAfterOrderings(Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> orderings){
        this.simpleAfterOrderings = orderings;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withExclusions(List<ItemProviderEntry<?, ?>> exclusions){
        this.simpleExclusions = exclusions;
        return this;
    }

    public MechanicalRegistrateDisplayItemsGenerator withExclusions(Supplier<List<ItemProviderEntry<?, ?>>> exclusions){
        this.simpleExclusions = exclusions.get();
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withTagDependentExclusions(List<ItemEntry<TagDependentIngredientItem>> tagDependentExclusions){
        this.tagDependentExclusion = tagDependentExclusions;
        return this;
    }
    public MechanicalRegistrateDisplayItemsGenerator withTagDependentExclusions(Supplier<List<ItemEntry<TagDependentIngredientItem>>> tagDependentExclusions){
        this.tagDependentExclusion = tagDependentExclusions.get();
        return this;
    }


    public record ItemOrdering(Item item, Item anchor, Type type) {
        public static ItemOrdering before(Item item, Item anchor) {
            return new ItemOrdering(item, anchor, Type.BEFORE);
        }

        public static ItemOrdering after(Item item, Item anchor) {
            return new ItemOrdering(item, anchor, Type.AFTER);
        }

        public enum Type {
            BEFORE,
            AFTER;
        }
    }
}
