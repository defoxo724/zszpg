import ListGroup from 'react-bootstrap/ListGroup'
import type { MealCategory } from '../../types'
import EntityActionButtons from '../common/EntityActionButtons'
import TruncatedText from '../common/TruncatedText'

interface MealCategoryListProps {
	categories: MealCategory[]
	isAdmin: boolean
	onEdit: (category: MealCategory) => void
	onDelete: (id: number) => void
}

const MealCategoryList = (props: MealCategoryListProps) => (
	<>
		<ListGroup>
			{props.categories.map((category) => (
				<ListGroup.Item
					className="d-flex justify-content-between align-items-center"
					key={category.id}
				>
					<div className="d-flex align-items-center gap-3 overflow-hidden">
						{category.imageUrl ? (
							<img
								src={category.imageUrl}
								alt={category.name}
								width={72}
								height={52}
								className="rounded object-fit-cover flex-shrink-0"
							/>
						) : (
							<span className="category-image-placeholder">Brak obrazu</span>
						)}
						<TruncatedText text={category.name} maxWidth="420px" />
					</div>
					{props.isAdmin && (
						<EntityActionButtons
							onEdit={() => props.onEdit(category)}
							onDelete={() => props.onDelete(category.id)}
						/>
					)}
				</ListGroup.Item>
			))}
		</ListGroup>
		{!props.categories.length && <p>Brak kategorii.</p>}
	</>
)

export default MealCategoryList
