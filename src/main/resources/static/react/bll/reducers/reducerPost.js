import apiPost from "../../api/apiPost";

const initialState = {
    posts: []
};

const CHANGE_POSTS = "CHANGE_POSTS";
const UPDATE_LIKES = "UPDATE_LIKES";
const CHANGE_COUNT_LIKES = "CHANGE_COUNT_LIKES";

const reducerPost = (state=initialState, action) => {
    switch (action.type) {
        case CHANGE_COUNT_LIKES:
            const newPosts = state.posts.map(p =>
                p.id === action.postId
                    ?{
                        ...p,
                        countLikes: action.countLikes
                    }
                    :p
            );

            return {
                ...state,
                posts: newPosts
            }
        case UPDATE_LIKES:
            const posts = state.posts.map(post=>{
                const info = action.likesById.find(info => info.postId === post.id);
                const hasLike = info !== undefined ?info.hasLike :post.hasLike
                return {
                    ...post,
                    hasLike
                }
            });

            return {
                ...state,
                posts
            }
        case CHANGE_POSTS:
            return {
                ...state,
                posts: action.posts
            }
        default: {
            return state;
        }
    }
}

export default reducerPost;

export const postActionCreator = {
    changePosts(posts) {
        return {
            type: CHANGE_POSTS,
            posts
        }
    },

    getLikesInfo(likesById) {
        return {
            type: UPDATE_LIKES,
            likesById
        }
    },

    changeCountLikes(countLikes, postId) {
        return {
            type: CHANGE_COUNT_LIKES,
            countLikes,
            postId
        }
    }
}

export const postGetters = {
    getPosts(state) {
        return state.reducerPost.posts;
    }
}

export const postThunkCreators = {
    getPosts() {
        return async (dispatch) => {
            const posts = await apiPost.getAllPosts();
            dispatch(postActionCreator.changePosts(posts));
        }
    },

    getLikesInfo(authorization, postsIds) {
        return async (dispatch) => {
            const likesById = await apiPost.getLikesInfo(authorization, postsIds);
            dispatch(postActionCreator.getLikesInfo(likesById));
        }
    }
}
