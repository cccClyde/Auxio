/*
 * Copyright (c) 2021 Auxio Project
 * SongListFragment.kt is part of Auxio.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.oxycblt.auxio.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.oxycblt.auxio.R
import org.oxycblt.auxio.music.Song
import org.oxycblt.auxio.ui.DisplayMode
import org.oxycblt.auxio.ui.SongViewHolder
import org.oxycblt.auxio.ui.SortMode
import org.oxycblt.auxio.ui.newMenu
import org.oxycblt.auxio.ui.sliceArticle
import org.oxycblt.auxio.util.applySpans

class SongListFragment : HomeListFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = SongsAdapter(
            doOnClick = { song ->
                playbackModel.playSong(song)
            },
            ::newMenu
        )

        setupRecycler(R.id.home_song_list, adapter, homeModel.songs)
        binding.homeRecycler.applySpans { it == 0 }

        return binding.root
    }

    override val popupProvider: (Int) -> String
        get() = { idx ->
            val song = homeModel.songs.value!![idx]

            when (homeModel.getSortForDisplay(DisplayMode.SHOW_SONGS)) {
                SortMode.ASCENDING, SortMode.DESCENDING -> song.name.sliceArticle()
                    .first().uppercase()

                SortMode.ARTIST -> song.album.artist.name.sliceArticle()
                    .first().uppercase()

                SortMode.ALBUM -> song.album.name.sliceArticle()
                    .first().uppercase()

                SortMode.YEAR -> song.album.year.toString()
            }
        }

    inner class SongsAdapter(
        private val doOnClick: (data: Song) -> Unit,
        private val doOnLongClick: (view: View, data: Song) -> Unit,
    ) : HomeAdapter<Song, SongViewHolder>() {
        override fun getItemCount(): Int = data.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
            return SongViewHolder.from(parent.context, doOnClick, doOnLongClick)
        }

        override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
            holder.bind(data[position])
        }
    }
}
