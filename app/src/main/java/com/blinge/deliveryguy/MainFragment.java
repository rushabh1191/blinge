package com.blinge.deliveryguy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import com.blinge.deliveryguy.model.ImageItem;
import com.blinge.deliveryguy.model.NoticeItem;
import com.blinge.deliveryguy.model.TextImageItem;
import com.blinge.deliveryguy.model.TextItem;
import com.blinge.deliveryguy.presenter.ImageCardPresenter;
import com.blinge.deliveryguy.presenter.NoticeCardPresenter;
import com.blinge.deliveryguy.presenter.TextCardPresenter;
import com.blinge.deliveryguy.presenter.TextImageCardPresenter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends BrowseSupportFragment {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.colorPrimary, null));
        loadRows();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }

    private void loadRows() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // Row 1: Text only
        ArrayObjectAdapter textAdapter = new ArrayObjectAdapter(new TextCardPresenter());
        textAdapter.add(new TextItem("Breaking News",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        textAdapter.add(new TextItem("Sports Update",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium totam rem aperiam."));
        textAdapter.add(new TextItem("Tech Review",
                "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque."));
        textAdapter.add(new TextItem("Weather Forecast",
                "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates."));
        textAdapter.add(new TextItem("Finance Report",
                "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat."));
        rowsAdapter.add(new ListRow(new HeaderItem(0, getString(R.string.row_text)), textAdapter));

        // Row 2: Images only
        ArrayObjectAdapter imageAdapter = new ArrayObjectAdapter(new ImageCardPresenter());
        imageAdapter.add(new ImageItem("https://picsum.photos/seed/tv1/400/225", "Landscape"));
        imageAdapter.add(new ImageItem("https://picsum.photos/seed/tv2/400/225", "City"));
        imageAdapter.add(new ImageItem("https://picsum.photos/seed/tv3/400/225", "Nature"));
        imageAdapter.add(new ImageItem("https://picsum.photos/seed/tv4/400/225", "Architecture"));
        imageAdapter.add(new ImageItem("https://picsum.photos/seed/tv5/400/225", "Abstract"));
        rowsAdapter.add(new ListRow(new HeaderItem(1, getString(R.string.row_images)), imageAdapter));

        // Row 3: Text & Images
        ArrayObjectAdapter textImageAdapter = new ArrayObjectAdapter(new TextImageCardPresenter());
        textImageAdapter.add(new TextImageItem("Mountain Adventure", "Explore the peaks",
                "https://picsum.photos/seed/ti1/400/225"));
        textImageAdapter.add(new TextImageItem("Ocean Breeze", "Feel the waves",
                "https://picsum.photos/seed/ti2/400/225"));
        textImageAdapter.add(new TextImageItem("City Lights", "Urban exploration",
                "https://picsum.photos/seed/ti3/400/225"));
        textImageAdapter.add(new TextImageItem("Forest Path", "Nature trails",
                "https://picsum.photos/seed/ti4/400/225"));
        textImageAdapter.add(new TextImageItem("Desert Sun", "Arid landscapes",
                "https://picsum.photos/seed/ti5/400/225"));
        rowsAdapter.add(new ListRow(new HeaderItem(2, getString(R.string.row_text_images)), textImageAdapter));

        // Row 4: Society Notices (fetched from Gmail server)
        ArrayObjectAdapter noticeAdapter = new ArrayObjectAdapter(new NoticeCardPresenter());
        rowsAdapter.add(new ListRow(new HeaderItem(3, getString(R.string.row_notices)), noticeAdapter));

        setAdapter(rowsAdapter);

        String apiUrl = getString(R.string.notices_api_url);
        executor.execute(() -> {
            try {
                List<NoticeItem> notices = NoticeRepository.fetchNotices(apiUrl);
                mainHandler.post(() -> noticeAdapter.addAll(notices));
            } catch (Exception ignored) {
                // Server unreachable — row stays empty, no crash
            }
        });
    }
}
