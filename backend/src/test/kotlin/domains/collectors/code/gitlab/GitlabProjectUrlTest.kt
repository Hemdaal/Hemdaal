package domains.collectors.code.gitlab

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test


internal class GitlabProjectUrlTest {

    @Test
    fun `should return project path`() {
        Truth.assertThat(GitlabProjectUrl("https://domains.collectors.code.gitlab.com/sample-group111/sample-private-project").getProjectPath())
            .isEqualTo("sample-group111/sample-private-project")
    }

    @Test
    fun `should return base url`() {
        Truth.assertThat(GitlabProjectUrl("https://domains.collectors.code.gitlab.com/sample-group111/sample-private-project").getBaseUrl())
            .isEqualTo("https://domains.collectors.code.gitlab.com:443")
    }
}
