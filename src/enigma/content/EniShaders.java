package enigma.content;

import arc.Core;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import static mindustry.Vars.tree;

public class EniShaders {


	public static class BlockShader extends Shader {
		public BlockShader(String name) {
			super(Core.files.internal("shaders/default.vert"),
					tree.get("shaders/" + name + ".frag"));
		}

		@Override
		public void apply() {
			setUniformf("u_time", Time.time / Scl.scl(1f));
			setUniformf("u_offset",
					Core.camera.position.x,
					Core.camera.position.y
			);
		}
	}
}
