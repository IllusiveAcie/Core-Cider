import itertools
from typing import Dict, NamedTuple, Sequence, Optional, Tuple, Any, Literal


class Rock(NamedTuple):
    category: str
    sand: str

class MetalTool(NamedTuple):
    primary: str
    handle: str
    head: bool = True

class Metal(NamedTuple):
    color = str
    secondary_color = Optional[str]
    icon_set = Literal['basic', 'metallic', 'soft']
    type: Literal['ingot', 'part', 'all']
    weathering: bool = False

    def has_block(self, item: str) -> bool: return self.has(METAL_BLOCKS[item])

    def has(self, item: MetalItem) -> bool:
        if item.type == 'weathering':
            return self.weathering
        if item.type == 'all':
            return self.type == 'all'
        if item.type == 'part':
            return self.type in ('all', 'part')
        if item.type == 'ingot':
            return True

class MetalItem(NamedTuple):
    type: Literal['ingot', 'part', 'all', 'weathering']
    mold: bool = False

METALS: dict[str, Metal] = {
    'bismuth': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'bismuth_bronze': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', False),
    'black_bronze': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', False),
    'bronze': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', True),
    'brass': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', True),
    'copper': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', True),
    'gold': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'nickel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'rose_gold': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'silver': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', True),
    'tin': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'zinc': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'sterling_silver': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', True),
    'wrought_iron': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', True),
    'cast_iron': Metal('#FFFFFF', '#FFFFFF', 'basic', 'part', False),
    'pig_iron': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', True),
    'black_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', False),
    'blue_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', False),
    'red_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'all', False),

    'weak_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'weak_blue_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'weak_red_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),

    'high_carbon_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'high_carbon_black_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'high_carbon_blue_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),
    'high_carbon_red_steel': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False),

    'unknown': Metal('#FFFFFF', '#FFFFFF', 'basic', 'ingot', False)
}

TOOLS: dict[str, MetalTool] = {
    'axe': MetalTool('axe', 'handle'), 
    'pickaxe': MetalTool('pickaxe', 'handle'), 
    'shovel': MetalTool('shovel', 'shovel_handle'), 
    'hoe': MetalTool('hoe', 'handle'), 
    'chisel': MetalTool('chisel', 'handle_rotated'), 
    'sword': MetalTool('sword', 'handle'), 
    'mace': MetalTool('mace', 'handle'), 
    'saw': MetalTool('saw', 'saw_handle'), 
    'knife': MetalTool('knife', 'knife_handle'), 
    'javelin': MetalTool('javelin', 'knife_handle'), 
    'hammer': MetalTool('hammer', 'handle'), 
    'propick': MetalTool('propick', 'handle'), 
    'scythe': MetalTool('scythe', 'scythe_handle'), 
    'shears': MetalTool('shears', 'shears_handle', False)
} 

METAL_ITEMS: dict[str, MetalItem] = {
    'ingot': MetalItem('ingot', True), 
    'double_ingot': MetalItem('part'),
    'sheet': MetalItem('part'),
    'double_sheet': MetalItem('part'),
    'rod': MetalItem('part'),

    'tuyere': MetalItem('all'), 
    'fishing_rod': MetalItem('all')
}

METAL_BLOCKS = ()

ROCKS = ('granite', 'diorite', 'gabbro', 'shale', 'claystone', 'limestone', 'conglomerate', 'dolomite', 'chert', 'chalk', 'tuff', 'rhyolite', 'basalt', 'andesite', 'dacite', 'quartzite', 'slate', 'phyllite', 'schist', 'gneiss', 'marble')
ROCK_ITEMS = ()
ROCK_BLOCKS = ()

WOODS = ('acacia', 'ash', 'aspen', 'birch', 'blackwood', 'chestnut', 'douglas_fir', 'hickory', 'kapok', 'mangrove', 'maple', 'oak', 'palm', 'pine', 'rosewood', 'sequoia', 'spruce', 'sycamore', 'white_cedar', 'willow')
WOOD_ITEMS = ()
WOOD_BLOCKS = ()

SAND_BLOCK_TYPES = ('brown', 'white', 'black', 'red', 'yellow', 'green', 'pink')
SOIL_BLOCK_VARIANTS = ('entisol', 'aridisol', 'oxisol', 'fluvisol', 'andisol', 'podzol', 'alfisol', 'mollisol')
NATURAL_SOIL_BLOCKS = ('dirt', 'duff', 'mud', 'clay', 'clay_duff', 'rooted_dirt', 'coarse_dirt', 'grass', 'clay_grass')

COLORS = ('white', 'orange', 'magenta', 'light_blue', 'yellow', 'lime', 'pink', 'gray', 'light_gray', 'cyan', 'purple', 'blue', 'brown', 'green', 'red', 'black')
NON_WHITE_COLORS = COLORS[1:]
GLASS_TYPES = ('silica', 'hematitic', 'olivine', 'volcanic')
POWDERS = ('flux', 'lime', 'salt', 'soda_ash', 'sulfur', 'wood_ash', 'charcoal', 'kaolinite', 'sylvite')

ORE_POWDERS = ('native_copper', 'native_silver', 'native_gold', 'hematite', 'cassiterite', 'bismuthinite', 'garnierite', 'malachite', 'magnetite', 'limonite', 'sphalerite', 'tetrahidrite', 'graphite', 'saltpeter', 'sulfur', 'sylvite')
GEM_POWDERS = ('lapis_lazuli', 'diamond', 'emarald', 'amthyst', 'opal', 'ruby', 'pyrite', 'sapphire', 'topaz')

SPREADING_BERRIES = ('blackberry', 'raspberry', 'blueberry', 'elderberry')
STATIONARY_BERRIES = ('snowberry', 'bunchberry', 'gooseberry', 'cloudberry', 'strawberry', 'wintergreen_berry')
FRUIT_TREES = ('banana', 'cherry', 'green_apple', 'red_apple', 'lemon', 'olive', 'orange', 'peach', 'plum')
JAR_PRESERVES = (*SPREADING_BERRIES, *STATIONARY_BERRIES, *FRUIT_TREES, 'melon_slice', 'peanut')

GRAINS = ('barley', 'maize', 'oat', 'rice', 'rye', 'wheat')
GRAIN_SUFFIXES = ('', '_grain', '_flour', '_dough', '_bread', '_bread_sandwich', '_bread_jam_sandwich')