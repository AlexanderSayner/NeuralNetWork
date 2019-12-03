#version 330

layout (location = 0) in vec3 position; // Устанавливаем позиция переменной с координатами в 0
layout (location = 1) in vec2 texture;

out vec2 textire_coordinates;

void main() {

    gl_Position = vec4(position, 1.0f);
    textire_coordinates = vec2(texture.x, 1.0f - texture.y);
}
