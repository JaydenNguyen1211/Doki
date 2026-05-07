package mazentas.doki.videoplayer.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import mazentas.doki.videoplayer.model.Video

class VideoPickerPreviewParameterProvider : PreviewParameterProvider<List<Video>> {
    override val values: Sequence<List<Video>>
        get() = sequenceOf(
            listOf(
                Video(
                    id = 1,
                    path = "/storage/emulated/0/Download/Bo Tu Nguy Hiem (2024) 720p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Bo Tu Nguy Hiem (2024) 720p BluRay x264.mp4",
                    nameWithExtension = "Bộ Tứ Nguy Hiểm (2024) 720p BluRay x264.mp4",
                    duration = 1200,
                    width = 1280,
                    height = 720,
                    size = 1000,
                ),

                Video(
                    id = 2,
                    path = "/storage/emulated/0/Download/Mat Biec (2019) 1080p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Mat Biec (2019) 1080p BluRay x264.mp4",
                    nameWithExtension = "Mắt Biếc (2019) 1080p BluRay x264.mp4",
                    duration = 1400,
                    width = 1920,
                    height = 1080,
                    size = 2000,
                ),

                Video(
                    id = 3,
                    path = "/storage/emulated/0/Download/Bo Gia (2021) 2160p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Bo Gia (2021) 2160p BluRay x264.mp4",
                    nameWithExtension = "Bố Già (2021) 2160p BluRay x264.mp4",
                    duration = 1500,
                    width = 3840,
                    height = 2160,
                    size = 3000,
                ),

                Video(
                    id = 4,
                    path = "/storage/emulated/0/Download/Toi Thay Hoa Vang Tren Co Xanh (2015) 720p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Toi Thay Hoa Vang Tren Co Xanh (2015) 720p BluRay x264.mp4",
                    nameWithExtension = "Tôi Thấy Hoa Vàng Trên Cỏ Xanh (2015) 720p BluRay x264.mp4",
                    duration = 1350,
                    width = 1280,
                    height = 720,
                    size = 4000,
                ),

                Video(
                    id = 5,
                    path = "/storage/emulated/0/Download/Em Chua 18 (2017) 1080p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Em Chua 18 (2017) 1080p BluRay x264.mp4",
                    nameWithExtension = "Em Chưa 18 (2017) 1080p BluRay x264.mp4",
                    duration = 1800,
                    width = 1920,
                    height = 1080,
                    size = 5000,
                ),

                Video(
                    id = 6,
                    path = "/storage/emulated/0/Download/Hai Phuong (2019) 1080p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Hai Phuong (2019) 1080p BluRay x264.mp4",
                    nameWithExtension = "Hai Phượng (2019) 1080p BluRay x264.mp4",
                    duration = 2000,
                    width = 1920,
                    height = 1080,
                    size = 6000,
                ),

                Video(
                    id = 7,
                    path = "/storage/emulated/0/Download/Cuoc Doi Cua Yến (2022) 1080p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Cuoc Doi Cua Yen (2022) 1080p BluRay x264.mp4",
                    nameWithExtension = "Cuộc Đời Của Yến (2022) 1080p BluRay x264.mp4",
                    duration = 2100,
                    width = 1920,
                    height = 1080,
                    size = 7000,
                ),

                Video(
                    id = 8,
                    path = "/storage/emulated/0/Download/Den Bu (2023) 2160p BluRay x264.mp4",
                    uriString = "file:///storage/emulated/0/Download/Den Bu (2023) 2160p BluRay x264.mp4",
                    nameWithExtension = "Đen Bú (2023) 2160p BluRay x264.mp4",
                    duration = 1500,
                    width = 3840,
                    height = 2160,
                    size = 8000,
                ),
            ),
        )
}
