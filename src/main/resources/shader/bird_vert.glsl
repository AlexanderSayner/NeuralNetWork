#version 330 core

layout (location = 0) in vec3 position; // Устанавливаем позиция переменной с координатами в 0
layout (location = 1) in vec2 textire_coordinates;

uniform mat4 pr_matrix; // В main классе
uniform mat4 view_matrix = mat4(1.0);
uniform mat4 mult_matrix = mat4(1.0);

out DATA
{
    vec2 textire_coordinates;
} vs_out;

void main()
{
    gl_Position = pr_matrix * view_matrix * mult_matrix * vec4(position.x, position.y, position.z, 1.0);
    vs_out.textire_coordinates = textire_coordinates;
}
