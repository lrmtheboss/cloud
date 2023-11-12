//
// MIT License
//
// Copyright (c) 2022 Alexander Söderberg & Contributors
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package cloud.commandframework.bukkit.parsers.selector;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.suggestion.SuggestionProvider;
import cloud.commandframework.bukkit.arguments.selector.SingleEntitySelector;
import java.util.Collections;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Argument type for parsing {@link SingleEntitySelector}. On Minecraft 1.13+
 * this argument uses Minecraft's built-in entity selector argument for parsing
 * and suggestions. On prior versions, this argument will suggest nothing and
 * always fail parsing with {@link SelectorParseException.FailureReason#UNSUPPORTED_VERSION}.
 *
 * @param <C> sender type
 */
public final class SingleEntitySelectorArgument<C> extends CommandArgument<C, SingleEntitySelector> {

    private SingleEntitySelectorArgument(
            final @NonNull String name,
            final @Nullable SuggestionProvider<C> suggestionProvider,
            final @NonNull ArgumentDescription defaultDescription
    ) {
        super(
                name,
                new SingleEntitySelectorParser<>(),
                SingleEntitySelector.class,
                suggestionProvider,
                defaultDescription
        );
    }

    /**
     * Create a new builder
     *
     * @param name Name of the argument
     * @param <C>  Command sender type
     * @return Created builder
     * @deprecated prefer {@link #builder(String)}
     */
    @API(status = API.Status.DEPRECATED, since = "1.8.0")
    @Deprecated
    public static <C> @NonNull Builder<C> newBuilder(final @NonNull String name) {
        return builder(name);
    }

    /**
     * Create a new {@link Builder}.
     *
     * @param name argument name
     * @param <C>  sender type
     * @return new builder
     * @since 1.8.0
     */
    @API(status = API.Status.STABLE, since = "1.8.0")
    public static <C> @NonNull Builder<C> builder(final @NonNull String name) {
        return new Builder<>(name);
    }

    /**
     * Create a new required command argument
     *
     * @param name Argument name
     * @param <C>  Command sender type
     * @return Created argument
     */
    public static <C> @NonNull SingleEntitySelectorArgument<C> of(final @NonNull String name) {
        return SingleEntitySelectorArgument.<C>builder(name).build();
    }


    public static final class Builder<C> extends CommandArgument.TypedBuilder<C, SingleEntitySelector, Builder<C>> {

        private Builder(final @NonNull String name) {
            super(SingleEntitySelector.class, name);
        }

        /**
         * Builder a new argument
         *
         * @return Constructed argument
         */
        @Override
        public @NonNull SingleEntitySelectorArgument<C> build() {
            return new SingleEntitySelectorArgument<>(
                    this.getName(),
                    this.suggestionProvider(),
                    this.getDefaultDescription()
            );
        }
    }


    public static final class SingleEntitySelectorParser<C> extends SelectorUtils.EntitySelectorParser<C, SingleEntitySelector> {

        /**
         * Creates a new {@link SingleEntitySelectorParser}.
         */
        public SingleEntitySelectorParser() {
            super(true);
        }

        @Override
        public SingleEntitySelector mapResult(
                final @NonNull String input,
                final SelectorUtils.@NonNull EntitySelectorWrapper wrapper
        ) {
            return new SingleEntitySelector(input, Collections.singletonList(wrapper.singleEntity()));
        }
    }
}
