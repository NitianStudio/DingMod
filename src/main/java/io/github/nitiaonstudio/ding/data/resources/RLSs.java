package io.github.nitiaonstudio.ding.data.resources;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import io.github.nitiaonstudio.ding.data.resources.util.*;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Getter
public enum RLSs {
    forge_anvil_block(ForgeAnvilBase::new),
    ingot(IngotBase::new),
    sword(SwordBase::new),
    lozenge(LozengeBase::new),
    gemstone(GemstoneBase::new),
    axe(AxeBase::new),
    pickaxe(PickaxeBase::new),
    hoe(HoeBase::new),
    chestplate(ChestplateBase::new),
    helmet(HelmetBase::new),
    leggings(LeggingsBase::new)
    ;
    private final Function<ConcurrentHashMap<ResourceLocation, RBI>, RLS> rls;

    RLSs(Function<ConcurrentHashMap<ResourceLocation, RBI>, RLS> rls) {
        this.rls = rls;
    }


    public static RLS getRLS(ConcurrentHashMap<ResourceLocation, RBI> cmp, RLSs rlSs) {
        return rlSs.rls.apply(cmp);
    }
}
