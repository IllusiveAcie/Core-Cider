from PIL import Image
import os

from image_utils import *
from constants import *

def save(image, path):
    directory = os.path.dirname(path)
    if directory:
        os.makedirs(directory, exist_ok=True)
    image.save(path)


def main():
    pass
