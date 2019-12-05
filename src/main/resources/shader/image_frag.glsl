#version 330

out vec4 color;

//in vec2 textire_coordinates;
in vec3 textire_coordinates;

uniform sampler2D ourTexture;

void main() {

//    color = texture(ourTexture, textire_coordinates);
    color=vec4(textire_coordinates,1.0);
}
