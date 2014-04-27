
attribute vec2 a_position;

attribute vec2 a_texCoord;
varying vec2 v_texCoord;

uniform float u_cameraZ;
uniform mat4 u_worldTransform;
uniform mat4 u_projView;

varying float v_distance;
varying vec2 v_position;

void main()
{
	v_texCoord = a_texCoord - a_texCoord * 0.5;
	
	vec4 pos = u_worldTransform * vec4(a_position.x, 0.0, a_position.y, 1.0);
	v_position = a_position;
	gl_Position = u_projView * pos;
	
	// Alpha
	v_distance = pos.z - u_cameraZ;//clamp(1.5 - (pos.z - u_cameraZ) / 100.0, 0.0, 1.0);
}
