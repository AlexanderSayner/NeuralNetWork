#version 330 core

layout (location = 0) in vec3 position;

uniform mat4 pr_matrix;

void main()
{
    //    gl_Position = vec4(position, 1.0f);
    gl_Position = pr_matrix * vec4(position, 1.0f);
}