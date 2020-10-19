import imageio
import os

path = '..\\Renderer\\images\\ChangeAperture'

filenames = []
for i in range(0, 50):
  filenames.append(str(i) + '.jpg')

with imageio.get_writer(path + '\\result.gif', mode='I', duration=0.06) as writer:
  for filename in filenames:
    image = imageio.imread(os.path.join(path, filename))
    writer.append_data(image)

  for filename in reversed(filenames):
    image = imageio.imread(os.path.join(path, filename))
    writer.append_data(image)
