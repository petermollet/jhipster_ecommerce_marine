import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeArticle, defaultValue } from 'app/shared/model/type-article.model';

export const ACTION_TYPES = {
  FETCH_TYPEARTICLE_LIST: 'typeArticle/FETCH_TYPEARTICLE_LIST',
  FETCH_TYPEARTICLE: 'typeArticle/FETCH_TYPEARTICLE',
  CREATE_TYPEARTICLE: 'typeArticle/CREATE_TYPEARTICLE',
  UPDATE_TYPEARTICLE: 'typeArticle/UPDATE_TYPEARTICLE',
  DELETE_TYPEARTICLE: 'typeArticle/DELETE_TYPEARTICLE',
  RESET: 'typeArticle/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeArticle>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TypeArticleState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeArticleState = initialState, action): TypeArticleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPEARTICLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPEARTICLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPEARTICLE):
    case REQUEST(ACTION_TYPES.UPDATE_TYPEARTICLE):
    case REQUEST(ACTION_TYPES.DELETE_TYPEARTICLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPEARTICLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPEARTICLE):
    case FAILURE(ACTION_TYPES.CREATE_TYPEARTICLE):
    case FAILURE(ACTION_TYPES.UPDATE_TYPEARTICLE):
    case FAILURE(ACTION_TYPES.DELETE_TYPEARTICLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEARTICLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEARTICLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPEARTICLE):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPEARTICLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPEARTICLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/type-articles';

// Actions

export const getEntities: ICrudGetAllAction<ITypeArticle> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TYPEARTICLE_LIST,
  payload: axios.get<ITypeArticle>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITypeArticle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPEARTICLE,
    payload: axios.get<ITypeArticle>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITypeArticle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPEARTICLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeArticle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPEARTICLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeArticle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPEARTICLE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
