#version 330 core

layout (location = 0) in vec3 position; // Устанавливаем позиция переменной с координатами в 0
layout (location = 1) in vec3 color;    // А позицию переменной с цветом в 1
layout (location = 2) in vec2 textire_coordinates; // Текстурки подвезли

out vec3 ourColor; // Цвет передаём далее фрагментному шейдеру
out vec2 texCoord;

uniform mat4 projectionMatrix;

void main()
{
    gl_Position = projectionMatrix * vec4(position.x, position.y, position.z, 1.0);
    ourColor = color; // Устанавливаем значение цвета, полученное от вершинных данных
    texCoord = textire_coordinates;
}
