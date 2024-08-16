package io.github.nitianstudio.ding.block;

import com.mojang.serialization.MapCodec;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.registry.RegistryBlock;
import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import java.util.Optional;

public class ForgeAnvilBlock extends FallingBlock implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<FallingBlock> CODEC = simpleCodec(ForgeAnvilBlock::new);
    private static final int FALL_DAMAGE_MAX = 40;
    public ForgeAnvilBlock(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected void spawnAfterBreak(@NotNull BlockState state,
                                   @NotNull ServerLevel level,
                                   @NotNull BlockPos pos,
                                   @NotNull ItemStack stack,
                                   boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeAnvilTileEntity entity) {
            if (entity.getStack() != ItemStack.EMPTY)
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), entity.getStack()));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {

        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state,
                                           @NotNull BlockGetter level,
                                           @NotNull BlockPos pos,
                                           @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> makeNorth();
            case EAST -> makeEast();
            case SOUTH -> makeSouth();
            default -> makeShape();
        };
    }

    //销毁
    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public @NotNull DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().anvil(entity);
    }



    //使用方块
    @Override
    @NotNull
    protected InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Player player, BlockHitResult hitResult) {
        Optional<ForgeAnvilTileEntity> blockEntity = level.getBlockEntity(pos, (BlockEntityType<ForgeAnvilTileEntity>) RegistryBlockTile.forge_anvil_block.get());
        blockEntity.ifPresent(entity -> {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            if (entity.getStack().isEmpty()) {

                if (!mainHandItem.isEmpty()) {
                    ItemStack copy = mainHandItem.copy();
                    copy.setCount(1);
                    entity.setStack(copy);
                    if (mainHandItem.getCount() == 1) {
                        player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        mainHandItem.setCount(mainHandItem.getCount() - 1);
                        player.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
                    }
                }
                else if (!offhandItem.isEmpty()) {
                    ItemStack copy = offhandItem.copy();
                    copy.setCount(1);
                    entity.setStack(copy);
                    if (offhandItem.getCount() == 1) {
                        player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    } else {
                        offhandItem.setCount(offhandItem.getCount() - 1);
                        player.setItemSlot(EquipmentSlot.OFFHAND, mainHandItem);
                    }
                }
            }
            else
            if (mainHandItem.isEmpty() && offhandItem.isEmpty()) {
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), entity.getStack().copy()));
                entity.setStack(ItemStack.EMPTY);
            }
        });// 装卸货
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.30776261965716256, 0.5153676784340807, 0.5573681312948406, 0.7367626196571626, 0.7566801784340806, 1.737118131294841), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3129106196571626, 0.05955517843408059, 0.4604141312948406, 0.7316146196571627, 0.7030551784340807, 0.7718681312948407), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.31805861965716264, 0.32768017843408065, 0.9162266312948406, 0.7298986196571626, 0.8639301784340807, 1.0668056312948404), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3094786196571626, 0.43493017843408066, 0.19228913129484054, 0.7333306196571626, 0.7298676784340807, 0.7718681312948407), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.31119461965716255, 0.22043017843408064, 0.24591413129484047, 0.7298986196571626, 0.5689926784340806, 0.6294401312948406), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.38991611965716255, 0.4593831784340807, -0.1294608687051596, 0.6797056196571626, 0.7543206784340806, 0.5305556312948406), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4167286196571626, 0.5130081784340806, -0.34396086870515974, 0.6528931196571626, 0.7543206784340806, -0.0056943687051594916), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09326261965716254, 0.005930178434080613, 0.7718681312948407, 0.9512626196571625, 0.22043017843408064, 0.9863681312948407), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28095011965716266, 0.005930178434080613, 0.2624306312948405, 0.7635751196571626, 0.11318017843408062, 0.9595556312948406), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09326261965716254, 0.005930178434080613, 0.12836813129484054, 0.9512626196571625, 0.22043017843408064, 0.3428681312948406), BooleanOp.OR);

        return shape;
    }

    public VoxelShape makeSouth(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.5423828143143252, 0.5153676784340807, 0.327595636637678, 1.7221328143143257, 0.7566801784340806, 0.756595636637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4454288143143253, 0.05955517843408059, 0.3327436366376779, 0.7568828143143254, 0.7030551784340807, 0.7514476366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9012413143143252, 0.32768017843408065, 0.33445963663767797, 1.051820314314325, 0.8639301784340807, 0.746299636637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.17730381431432518, 0.43493017843408066, 0.331027636637678, 0.7568828143143254, 0.7298676784340807, 0.754879636637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.2309288143143251, 0.22043017843408064, 0.33445963663767797, 0.6144548143143251, 0.5689926784340806, 0.7531636366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.1444461856856749, 0.4593831784340807, 0.384652636637678, 0.5155703143143253, 0.7543206784340806, 0.674442136637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.35894618568567505, 0.5130081784340806, 0.411465136637678, -0.020679685685674798, 0.7543206784340806, 0.6476296366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7568828143143254, 0.005930178434080613, 0.11309563663767802, 0.9713828143143253, 0.22043017843408064, 0.971095636637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.24744531431432515, 0.005930178434080613, 0.300783136637678, 0.9445703143143253, 0.11318017843408062, 0.7834081366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.11338281431432518, 0.005930178434080613, 0.11309563663767802, 0.3278828143143253, 0.22043017843408064, 0.971095636637678), BooleanOp.OR);

        return shape;
    }

    public VoxelShape makeEast(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3126103196571627, 0.5153676784340807, -0.6577745580194851, 0.7416103196571626, 0.7566801784340806, 0.5219754419805153), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3177583196571626, 0.05955517843408059, 0.30747544198051524, 0.7364623196571626, 0.7030551784340807, 0.6189294419805152), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.31947431965716266, 0.32768017843408065, 0.012537941980515444, 0.7313143196571626, 0.8639301784340807, 0.16311694198051535), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3160423196571627, 0.43493017843408066, 0.30747544198051524, 0.7398943196571627, 0.7298676784340807, 0.8870544419805153), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.31947431965716266, 0.22043017843408064, 0.44990344198051535, 0.7381783196571626, 0.5689926784340806, 0.8334294419805155), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3696673196571627, 0.4593831784340807, 0.5487879419805153, 0.6594568196571626, 0.7543206784340806, 1.2088044419805155), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3964798196571626, 0.5130081784340806, 1.0850379419805152, 0.6326443196571626, 0.7543206784340806, 1.4233044419805156), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09811031965716266, 0.005930178434080613, 0.09297544198051527, 0.9561103196571626, 0.22043017843408064, 0.30747544198051524), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28579781965716267, 0.005930178434080613, 0.11978794198051529, 0.7684228196571626, 0.11318017843408062, 0.8169129419805155), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09811031965716266, 0.005930178434080613, 0.7364754419805153, 0.9561103196571626, 0.22043017843408064, 0.9509754419805154), BooleanOp.OR);

        return shape;
    }

    public VoxelShape makeNorth(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.6727598750000003, 0.5153676784340807, 0.3227479366376779, 0.506990125, 0.7566801784340806, 0.7517479366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.29249012499999993, 0.05955517843408059, 0.3278959366376779, 0.6039441249999999, 0.7030551784340807, 0.746599936637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.0024473749999999184, 0.32768017843408065, 0.33304393663767795, 0.148131625, 0.8639301784340807, 0.7448839366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.29249012499999993, 0.43493017843408066, 0.3244639366376779, 0.872069125, 0.7298676784340807, 0.7483159366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.43491812500000004, 0.22043017843408064, 0.3261799366376779, 0.8184441250000001, 0.5689926784340806, 0.7448839366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.533802625, 0.4593831784340807, 0.4049014366376779, 1.193819125, 0.7543206784340806, 0.6946909366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.070052625, 0.5130081784340806, 0.43171393663767793, 1.4083191250000002, 0.7543206784340806, 0.667878436637678), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.07799012499999997, 0.005930178434080613, 0.1082479366376779, 0.29249012499999993, 0.22043017843408064, 0.9662479366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.10480262499999998, 0.005930178434080613, 0.29593543663767796, 0.801927625, 0.11318017843408062, 0.7785604366376779), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.721490125, 0.005930178434080613, 0.1082479366376779, 0.935990125, 0.22043017843408064, 0.9662479366376779), BooleanOp.OR);
        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return RegistryBlockTile.forge_anvil_block.get().create(pos, state);
    }
}
