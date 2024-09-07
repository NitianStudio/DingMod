package io.github.nitiaonstudio.ding.data.resources;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import io.github.nitiaonstudio.ding.data.resources.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Getter
public enum RLSs {
    forge_anvil_block(ForgeAnvilBase::new),
    chestplate(ChestplateBase::new),
    helmet(HelmetBase::new),
    leggings(LeggingsBase::new),
    boots(BootsBase::new)
    ;
    private final Function<ConcurrentHashMap<ResourceLocation, RBI>, RLS> rls;

    private static final AtomicReference<ConcurrentHashMap<ResourceLocation, RBI>> cmp = new AtomicReference<>();

    RLSs(Function<ConcurrentHashMap<ResourceLocation, RBI>, RLS> rls) {
        this.rls = rls;
    }

    public static void setCmp(ConcurrentHashMap<ResourceLocation, RBI> tCmp) {
        cmp.set(tCmp);
    }

    public static ConcurrentHashMap<ResourceLocation, RBI> getCmp() {
        return cmp.get();
    }


    public RLS getRLS() {
        return rls.apply(getCmp());
    }
}
