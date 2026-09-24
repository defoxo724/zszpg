import type { Meal } from '../../types'
import EntityActionButtons from '../common/EntityActionButtons'
import TruncatedText from '../common/TruncatedText'
import { formatPrice } from '../../utils/formatPrice'

interface MealTableRowProps {
	meal: Meal
	categoryName: string
	isAdmin: boolean
	onEdit: (meal: Meal) => void
	onDelete: (id: number) => void
}

const MealTableRow = (props: MealTableRowProps) => (
	<tr>
		<td>
			{props.meal.imageUrl ? (
				<img
					src={props.meal.imageUrl}
					alt={props.meal.name}
					width={80}
					height={60}
					className="rounded object-fit-cover"
				/>
			) : (
				<span className="text-secondary">Brak</span>
			)}
		</td>
		<td>{props.meal.name}</td>
		<td>
			<TruncatedText text={props.meal.description} />
		</td>
		<td>{formatPrice(Number(props.meal.price))}</td>
		<td>{props.categoryName}</td>
		{props.isAdmin && (
			<td className="text-nowrap">
				<EntityActionButtons
					onEdit={() => props.onEdit(props.meal)}
					onDelete={() => props.onDelete(props.meal.id)}
				/>
			</td>
		)}
	</tr>
)

export default MealTableRow
