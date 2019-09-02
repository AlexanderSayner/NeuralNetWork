#version 330 core

layout (location = 0) in vec3 position; // Устанавливаем позиция переменной с координатами в 0
layout (location = 2) in vec2 textire_coordinates;

uniform mat4 pr_matrix;

out DATA
{
    vec2 textire_coordinates;
} vs_out;

void main()
{
    gl_Position = pr_matrix * vec4(position.x, position.y, position.z, 1.0);
    vs_out.textire_coordinates = textire_coordinates;
}
