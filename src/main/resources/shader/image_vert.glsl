#version 330

layout (location = 0) in vec3 position; // Устанавливаем позиция переменной с координатами в 0
layout (location = 1) in vec2 texture;

out vec2 textire_coordinates;

uniform float scale;
uniform mat4 multi_matrix = mat4(1.0);

void main() {

    gl_Position = multi_matrix * vec4(position * scale, 1.0f);
    textire_coordinates = vec2(texture.x, 1.0f - texture.y);
}
