from PIL import Image

def generate_greyscale(image_dir):
    img = Image.open(image_dir + ".png").convert('RGBA')
    grayscale_img = img.convert('LA')
    return grayscale_img

def get_rgb(color: str):
    if color.startswith('#'):
        val = color.lstrip('#')
        return tuple(int(val[i:i+2], 16) for i in (0, 2, 4))
    else:
        return color

def get_rgba(color: str, alpha: int = 255):
    if color.startswith('#'):
        val = color.lstrip('#')

        if len(val) == 8:
            return tuple(int(val[i:i+2], 16) for i in (0, 2, 4, 6))
        else:
            return tuple(int(val[i:i+2], 16) for i in (0, 2, 4)) + (alpha,)
    else:
        return color

def colorize(img: Image, color):
    img = img.convert('LA')
    for x in range(img.width):
        for y in range(img.height):
            r, g, b, a = img.getpixel((x, y))
            factor = r
            new = multiply(factor, color)
            img.putpixel((x, y), (*new, a))

    return img

def multiply(factor, colors):
    brightness = factor / 255
    return tuple(int(c * brightness) for c in colors)

def overlay(front_file_dir, back_file_dir, mask: str = None):
    foreground = Image.open(front_file_dir + '.png').convert('RGBA')
    background = Image.open(back_file_dir + '.png').convert('RGBA')
    if mask is None:
        mask = foreground
    else:
        mask = Image.open(mask + '.png').convert('L')
    background.paste(foreground, (0, 0), mask)
    return background

def alpha_overlay(front_file_dir, back_file_dir) :
    foreground = Image.open(front_file_dir + '.png').convert('RGBA')
    background = Image.open(back_file_dir + '.png').convert('RGBA')

    background.alpha_composite(foreground)
    return background

def palette_swap(img: Image, palette_key: Image, palette: Image) -> Image:
    data = {}
    for x in range(0, palette_key.width):
        data[palette_key.getpixel((x, 0))] = palette.getpixel((x, 0))
    for x in range(0, img.width):
        for y in range(0, img.height):
            dat = img.getpixel((x, y))
            if dat in data:
                img.putpixel((x, y), data[dat])
    return img